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

API_URL = "http://localhost:8080/api/crawler?team=team"

# CSV-Dateiname
CSV_FILE = "sofifa_players.csv"

# Chrome Optionen
chrome_options = Options()
chrome_options.add_argument("--headless")  # Kopflose Ausführung
chrome_options.add_argument("--no-sandbox")
chrome_options.add_argument("--disable-dev-shm-usage")

def get_player_urls():
    resp = requests.get(API_URL)
    resp.raise_for_status()
    return resp.json()

def parse_player(url, driver):
    """Öffnet die Sofifa-Seite im Browser und extrahiert Spielerinfos."""
    driver.get(url)

    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, "div.info h1"))
        )
    except:
        print(f"Timeout beim Laden der Seite: {url}")

    soup = BeautifulSoup(driver.page_source, "html.parser")

    # Name
    full_name = None
    info_div = soup.find("div", class_="info")
    if info_div and info_div.h1:
        full_name = info_div.h1.text.strip()
    else:
        h1_tag = soup.find("h1")
        if h1_tag:
            full_name = h1_tag.text.strip()
    if not full_name:
        raise ValueError("Spielername nicht gefunden")
    first_name, last_name = full_name.split(" ", 1)

    # Geburtsdatum, Größe, Gewicht
    birth_date = ""
    height = 0
    weight = 0
    data_div = soup.find("div", class_="meta")
    if data_div:
        birth_date_match = re.search(r'(\d{4}-\d{2}-\d{2})', data_div.text)
        birth_date = birth_date_match.group(1) if birth_date_match else ""
        height_match = re.search(r'(\d+)cm', data_div.text)
        height = int(height_match.group(1)) if height_match else 0
        weight_match = re.search(r'(\d+)kg', data_div.text)
        weight = int(weight_match.group(1)) if weight_match else 0

    # Nation
    nation_tag = data_div.find("a", href=re.compile("/nation/")) if data_div else None
    nation = nation_tag.text.strip() if nation_tag else ""

    # Team
    team_tag = soup.find("a", href=re.compile("/team/"))
    team_name = team_tag.text.strip() if team_tag else ""

    # Marktwert
    value_match = re.search(r'([\d\.]+)', soup.text.replace(",", ""))
    value = float(value_match.group(1)) if value_match else 0

    # Attribute (34 Werte)
    attributes = []
    attr_divs = soup.find_all("div", class_="bp3-text-overflow-ellipsis")
    for attr in attr_divs:
        text = attr.text.strip()
        if text.isdigit():
            attributes.append(int(text))
    while len(attributes) < 34:
        attributes.append(0)

    return [first_name, last_name, birth_date, height, weight, value, nation, team_name, False] + attributes[:34]

def main():
    player_urls = get_player_urls()
    print(f"Gefundene Spieler-URLs: {len(player_urls)}")

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)

    with open(CSV_FILE, "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        writer.writerow(
            ["firstName","lastName","birthDate","height","weight","value","nation","teamName","active"] +
            [f"attr{i+1}" for i in range(34)]
        )

        for url in player_urls:
            try:
                row = parse_player(url, driver)
                writer.writerow(row)
                print(f"Erfasst: {row[0]} {row[1]}")
            except Exception as e:
                print(f"Fehler bei {url}: {e}")

    driver.quit()
    print(f"CSV-Datei erstellt: {CSV_FILE}")

if __name__ == "__main__":
    main()
