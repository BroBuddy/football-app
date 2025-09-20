import requests
from bs4 import BeautifulSoup
import csv
import re
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager

STATIC_PLAYER_URL = "https://www.fifacm.com/player/209331/mohamed-salah"

# CSV-Dateiname
CSV_FILE = "fifacm_players_selenium_fixed.csv"

# Chrome Optionen
chrome_options = Options()
chrome_options.add_argument("--headless")  # Kopflose Ausführung
chrome_options.add_argument("--no-sandbox")
chrome_options.add_argument("--disable-dev-shm-usage")

def get_player_urls():
    """
    Gibt eine statische Liste mit der einzigen URL von Mohamed Salah zurück.
    """
    return [STATIC_PLAYER_URL]

def parse_player(url, driver):
    """Öffnet die fifacm-Seite im Browser und extrahiert Spielerinfos."""
    driver.get(url)

    # Erhöhe das Zeitlimit auf 30 Sekunden
    try:
        WebDriverWait(driver, 30).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, "div.player-profile h1"))
        )
    except Exception as e:
        print(f"Timeout beim Laden der Seite: {url} - {e}")
        return None

    soup = BeautifulSoup(driver.page_source, "html.parser")

    # Name
    full_name_tag = soup.select_one("div.player-profile h1")
    if not full_name_tag:
        raise ValueError("Spielername nicht gefunden")
    full_name = full_name_tag.text.strip()
    first_name, last_name = full_name.rsplit(" ", 1) if " " in full_name else (full_name, "")

    # Geburtsdatum, Größe, Gewicht
    birth_date = ""
    height = 0
    weight = 0
    player_details_div = soup.find("div", class_="player-info")
    if player_details_div:
        info_text = player_details_div.text

        birth_date_match = re.search(r'DOB: (\d{2}\.\d{2}\.\d{4})', info_text)
        if birth_date_match:
            day, month, year = birth_date_match.group(1).split('.')
            birth_date = f"{year}-{month}-{day}"

        height_match = re.search(r'Height: (\d+)cm', info_text)
        height = int(height_match.group(1)) if height_match else 0

        weight_match = re.search(r'Weight: (\d+)kg', info_text)
        weight = int(weight_match.group(1)) if weight_match else 0

    # Nation
    nation_tag = soup.select_one("div.player-profile .nation-flag")
    nation = nation_tag.get("title") if nation_tag else ""

    # Team
    team_tag = soup.select_one("div.player-club a img")
    team_name = team_tag.get("alt") if team_tag and team_tag.get("alt") else ""

    # Marktwert
    value_tag = soup.select_one("div.player-market-value span.value")
    value = 0
    if value_tag:
        value_text = value_tag.text.strip().replace("€", "").replace(",", "")
        if "M" in value_text:
            value = float(value_text.replace("M", "")) * 1_000_000
        elif "K" in value_text:
            value = float(value_text.replace("K", "")) * 1_000
        else:
            value = float(value_text)

    # Attribute (34 Werte)
    attributes = []
    attr_sections = soup.find_all("div", class_="player-attributes-stats")
    for section in attr_sections:
        for attr_row in section.find_all("div", class_="row"):
            attr_value_tag = attr_row.find("span", class_="label-stat")
            if attr_value_tag:
                attributes.append(int(attr_value_tag.text.strip()))

    # Sicherstellen, dass die Liste 34 Werte hat (mit 0-Füllung)
    while len(attributes) < 34:
        attributes.append(0)

    return [first_name, last_name, birth_date, height, weight, value, nation, team_name, False] + attributes[:34]

def main():
    player_urls = get_player_urls()
    print(f"Gefundene Spieler-URLs: {len(player_urls)}")

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)

    with open(CSV_FILE, "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)

        # Header für die CSV-Datei
        header = ["firstName", "lastName", "birthDate", "height", "weight", "value", "nation", "teamName", "active"]
        header += [f"attr{i+1}" for i in range(34)] # Generischer Header für Attribute
        writer.writerow(header)

        for url in player_urls:
            try:
                row = parse_player(url, driver)
                if row:
                    writer.writerow(row)
                    print(f"Erfasst: {row[0]} {row[1]}")
            except Exception as e:
                print(f"Fehler bei {url}: {e}")

    driver.quit()
    print(f"CSV-Datei erstellt: {CSV_FILE}")

if __name__ == "__main__":
    main()