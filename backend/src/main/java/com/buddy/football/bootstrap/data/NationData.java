package com.buddy.football.bootstrap.data;

import com.buddy.football.nation.entity.Nation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NationData {

    public static List<Nation> get() {
        LocalDateTime now = LocalDateTime.now();

        return List.of(
                    europe(),
                    asia(),
                    africa(),
                    northAmerica(),
                    southAmerica(),
                    oceania()
            ).stream()
            .flatMap(List::stream)
            .map(data -> new Nation(
                    data[0],
                    data[1],
                    "https://flagpedia.net/data/flags/h80/" + data[2] + ".png",
                    now,
                    now
            ))
            .toList();
    }

    private static List<String[]> europe() {
        return List.of(
                new String[]{"Albania", "ALB", "al"},
                new String[]{"Andorra", "AND", "ad"},
                new String[]{"Austria", "AUT", "at"},
                new String[]{"Belgium", "BEL", "be"},
                new String[]{"Bosnia and Herzegovina", "BIH", "ba"},
                new String[]{"Bulgaria", "BGR", "bg"},
                new String[]{"Croatia", "HRV", "hr"},
                new String[]{"Czech Republic", "CZE", "cz"},
                new String[]{"Denmark", "DNK", "dk"},
                new String[]{"England", "ENG", "gb-eng"},
                new String[]{"Estonia", "EST", "ee"},
                new String[]{"Finland", "FIN", "fi"},
                new String[]{"France", "FRA", "fr"},
                new String[]{"Germany", "DEU", "de"},
                new String[]{"Greece", "GRC", "gr"},
                new String[]{"Hungary", "HUN", "hu"},
                new String[]{"Iceland", "ISL", "is"},
                new String[]{"Ireland", "IRL", "ie"},
                new String[]{"Italy", "ITA", "it"},
                new String[]{"Kosovo", "XKX", "xk"},
                new String[]{"Latvia", "LVA", "lv"},
                new String[]{"Liechtenstein", "LIE", "li"},
                new String[]{"Lithuania", "LTU", "lt"},
                new String[]{"Luxembourg", "LUX", "lu"},
                new String[]{"Malta", "MLT", "mt"},
                new String[]{"Moldova", "MDA", "md"},
                new String[]{"Monaco", "MCO", "mc"},
                new String[]{"Montenegro", "MNE", "me"},
                new String[]{"Netherlands", "NLD", "nl"},
                new String[]{"North Macedonia", "MKD", "mk"},
                new String[]{"Norway", "NOR", "no"},
                new String[]{"Poland", "POL", "pl"},
                new String[]{"Portugal", "PRT", "pt"},
                new String[]{"Romania", "ROU", "ro"},
                new String[]{"San Marino", "SMR", "sm"},
                new String[]{"Scotland", "SCT", "gb-sct"},
                new String[]{"Serbia", "SRB", "rs"},
                new String[]{"Slovakia", "SVK", "sk"},
                new String[]{"Slovenia", "SVN", "si"},
                new String[]{"Spain", "ESP", "es"},
                new String[]{"Sweden", "SWE", "se"},
                new String[]{"Switzerland", "CHE", "ch"},
                new String[]{"Ukraine", "UKR", "ua"},
                new String[]{"Vatican City", "VAT", "va"},
                new String[]{"Wales", "WLS", "gb-wls"}
        );
    }

    private static List<String[]> asia() {
        return List.of(
                new String[]{"Afghanistan", "AFG", "af"},
                new String[]{"Armenia", "ARM", "am"},
                new String[]{"Azerbaijan", "AZE", "az"},
                new String[]{"Bahrain", "BHR", "bh"},
                new String[]{"Bangladesh", "BGD", "bd"},
                new String[]{"Bhutan", "BTN", "bt"},
                new String[]{"Brunei", "BRN", "bn"},
                new String[]{"Cambodia", "KHM", "kh"},
                new String[]{"China", "CHN", "cn"},
                new String[]{"Cyprus", "CYP", "cy"},
                new String[]{"Georgia", "GEO", "ge"},
                new String[]{"India", "IND", "in"},
                new String[]{"Indonesia", "IDN", "id"},
                new String[]{"Iran", "IRN", "ir"},
                new String[]{"Iraq", "IRQ", "iq"},
                new String[]{"Israel", "ISR", "il"},
                new String[]{"Japan", "JPN", "jp"},
                new String[]{"Jordan", "JOR", "jo"},
                new String[]{"Kazakhstan", "KAZ", "kz"},
                new String[]{"Kuwait", "KWT", "kw"},
                new String[]{"Kyrgyzstan", "KGZ", "kg"},
                new String[]{"Laos", "LAO", "la"},
                new String[]{"Lebanon", "LBN", "lb"},
                new String[]{"Malaysia", "MYS", "my"},
                new String[]{"Maldives", "MDV", "mv"},
                new String[]{"Mongolia", "MNG", "mn"},
                new String[]{"Myanmar", "MMR", "mm"},
                new String[]{"Nepal", "NPL", "np"},
                new String[]{"North Korea", "PRK", "kp"},
                new String[]{"Oman", "OMN", "om"},
                new String[]{"Pakistan", "PAK", "pk"},
                new String[]{"Palestine", "PSE", "ps"},
                new String[]{"Philippines", "PHL", "ph"},
                new String[]{"Russia", "RUS", "ru"},
                new String[]{"Qatar", "QAT", "qa"},
                new String[]{"Saudi Arabia", "SAU", "sa"},
                new String[]{"Singapore", "SGP", "sg"},
                new String[]{"South Korea", "KOR", "kr"},
                new String[]{"Sri Lanka", "LKA", "lk"},
                new String[]{"Syria", "SYR", "sy"},
                new String[]{"Taiwan", "TWN", "tw"},
                new String[]{"Tajikistan", "TJK", "tj"},
                new String[]{"Thailand", "THA", "th"},
                new String[]{"Timor-Leste", "TLS", "tl"},
                new String[]{"Turkey", "TUR", "tr"},
                new String[]{"Turkmenistan", "TKM", "tm"},
                new String[]{"United Arab Emirates", "ARE", "ae"},
                new String[]{"Uzbekistan", "UZB", "uz"},
                new String[]{"Vietnam", "VNM", "vn"},
                new String[]{"Yemen", "YEM", "ye"}
        );
    }

    private static List<String[]> africa() {
        return List.of(
                new String[]{"Algeria", "DZA", "dz"},
                new String[]{"Angola", "AGO", "ao"},
                new String[]{"Benin", "BEN", "bj"},
                new String[]{"Botswana", "BWA", "bw"},
                new String[]{"Burkina Faso", "BFA", "bf"},
                new String[]{"Burundi", "BDI", "bi"},
                new String[]{"Cabo Verde", "CPV", "cv"},
                new String[]{"Cameroon", "CMR", "cm"},
                new String[]{"Central African Republic", "CAF", "cf"},
                new String[]{"Chad", "TCD", "td"},
                new String[]{"Comoros", "COM", "km"},
                new String[]{"Congo", "COG", "cg"},
                new String[]{"Democratic Republic of the Congo", "COD", "cd"},
                new String[]{"Djibouti", "DJI", "dj"},
                new String[]{"Egypt", "EGY", "eg"},
                new String[]{"Equatorial Guinea", "GNQ", "gq"},
                new String[]{"Eritrea", "ERI", "er"},
                new String[]{"Eswatini", "SWZ", "sz"},
                new String[]{"Ethiopia", "ETH", "et"},
                new String[]{"Gabon", "GAB", "ga"},
                new String[]{"Gambia", "GMB", "gm"},
                new String[]{"Ghana", "GHA", "gh"},
                new String[]{"Guinea", "GIN", "gn"},
                new String[]{"Guinea-Bissau", "GNB", "gw"},
                new String[]{"Ivory Coast", "CIV", "ci"},
                new String[]{"Kenya", "KEN", "ke"},
                new String[]{"Lesotho", "LSO", "ls"},
                new String[]{"Liberia", "LBR", "lr"},
                new String[]{"Libya", "LBY", "ly"},
                new String[]{"Madagascar", "MDG", "mg"},
                new String[]{"Malawi", "MWI", "mw"},
                new String[]{"Mali", "MLI", "ml"},
                new String[]{"Mauritania", "MRT", "mr"},
                new String[]{"Mauritius", "MUS", "mu"},
                new String[]{"Morocco", "MAR", "ma"},
                new String[]{"Mozambique", "MOZ", "mz"},
                new String[]{"Namibia", "NAM", "na"},
                new String[]{"Niger", "NER", "ne"},
                new String[]{"Nigeria", "NGA", "ng"},
                new String[]{"Rwanda", "RWA", "rw"},
                new String[]{"São Tomé and Príncipe", "STP", "st"},
                new String[]{"Senegal", "SEN", "sn"},
                new String[]{"Seychelles", "SYC", "sc"},
                new String[]{"Sierra Leone", "SLE", "sl"},
                new String[]{"Somalia", "SOM", "so"},
                new String[]{"South Africa", "ZAF", "za"},
                new String[]{"South Sudan", "SSD", "ss"},
                new String[]{"Sudan", "SDN", "sd"},
                new String[]{"Tanzania", "TZA", "tz"},
                new String[]{"Togo", "TGO", "tg"},
                new String[]{"Tunisia", "TUN", "tn"},
                new String[]{"Uganda", "UGA", "ug"},
                new String[]{"Zambia", "ZMB", "zm"},
                new String[]{"Zimbabwe", "ZWE", "zw"}
        );
    }

    private static List<String[]> northAmerica() {
        return List.of(
                new String[]{"Antigua and Barbuda", "ATG", "ag"},
                new String[]{"Bahamas", "BHS", "bs"},
                new String[]{"Barbados", "BRB", "bb"},
                new String[]{"Belize", "BLZ", "bz"},
                new String[]{"Canada", "CAN", "ca"},
                new String[]{"Costa Rica", "CRI", "cr"},
                new String[]{"Cuba", "CUB", "cu"},
                new String[]{"Dominica", "DMA", "dm"},
                new String[]{"Dominican Republic", "DOM", "do"},
                new String[]{"El Salvador", "SLV", "sv"},
                new String[]{"Grenada", "GRD", "gd"},
                new String[]{"Guatemala", "GTM", "gt"},
                new String[]{"Haiti", "HTI", "ht"},
                new String[]{"Honduras", "HND", "hn"},
                new String[]{"Jamaica", "JAM", "jm"},
                new String[]{"Mexico", "MEX", "mx"},
                new String[]{"Nicaragua", "NIC", "ni"},
                new String[]{"Panama", "PAN", "pa"},
                new String[]{"Saint Kitts and Nevis", "KNA", "kn"},
                new String[]{"Saint Lucia", "LCA", "lc"},
                new String[]{"Saint Vincent and the Grenadines", "VCT", "vc"},
                new String[]{"Trinidad and Tobago", "TTO", "tt"},
                new String[]{"United States", "USA", "us"}
        );
    }

    private static List<String[]> southAmerica() {
        return List.of(
                new String[]{"Argentina", "ARG", "ar"},
                new String[]{"Bolivia", "BOL", "bo"},
                new String[]{"Brazil", "BRA", "br"},
                new String[]{"Chile", "CHL", "cl"},
                new String[]{"Colombia", "COL", "co"},
                new String[]{"Ecuador", "ECU", "ec"},
                new String[]{"Guyana", "GUY", "gy"},
                new String[]{"Paraguay", "PRY", "py"},
                new String[]{"Peru", "PER", "pe"},
                new String[]{"Suriname", "SUR", "sr"},
                new String[]{"Uruguay", "URY", "uy"},
                new String[]{"Venezuela", "VEN", "ve"}
        );
    }

    private static List<String[]> oceania() {
        return List.of(
                new String[]{"Australia", "AUS", "au"},
                new String[]{"Fiji", "FJI", "fj"},
                new String[]{"Kiribati", "KIR", "ki"},
                new String[]{"Marshall Islands", "MHL", "mh"},
                new String[]{"Micronesia", "FSM", "fm"},
                new String[]{"Nauru", "NRU", "nr"},
                new String[]{"New Zealand", "NZL", "nz"},
                new String[]{"Palau", "PLW", "pw"},
                new String[]{"Papua New Guinea", "PNG", "pg"},
                new String[]{"Samoa", "WSM", "ws"},
                new String[]{"Solomon Islands", "SLB", "sb"},
                new String[]{"Tonga", "TON", "to"},
                new String[]{"Tuvalu", "TUV", "tv"},
                new String[]{"Vanuatu", "VUT", "vu"}
        );
    }
}
