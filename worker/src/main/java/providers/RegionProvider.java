package providers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RegionProvider {
    Map<String, String> states;
    Map<String, List<String>> cities;

    public RegionProvider() {
        // populate states
        states = new HashMap<>();
        states.put("Alabama", "us_alabama");
        states.put("Arizona", "us_arizona");
        states.put("Arkansas", "us_arkansas");
        states.put("California", "us_california");
        states.put("Colorado", "us_colorado");
        states.put("Connecticut", "us_connecticut");
        states.put("Florida", "us_florida");
        states.put("Georgia", "us_georgia");
        states.put("Hawaii", "us_hawaii");
        states.put("Illinois", "us_illinois");
        states.put("Indiana", "us_indiana");
        states.put("Kansas", "us_kansas");
        states.put("Kentucky", "us_kentucky");
        states.put("Louisiana", "us_louisiana");
        states.put("Maryland", "us_maryland");
        states.put("Massachusetts", "us_massachusetts");
        states.put("Michigan", "us_michigan");
        states.put("Minnesota", "us_minnesota");
        states.put("Missouri", "us_missouri");
        states.put("Nebraska", "us_nebraska");
        states.put("Nevada", "us_nevada");
        states.put("New Hampshire", "us_new_hampshire");
        states.put("New Jersey", "us_new_jersey");
        states.put("New Mexico", "us_new_mexico");
        states.put("New York", "us_new_york");
        states.put("North Carolina", "us_north_carolina");
        states.put("Ohio", "us_ohio");
        states.put("Oklahoma", "us_oklahoma");
        states.put("Oregon", "us_oregon");
        states.put("Pennsylvania", "us_pennsylvania");
        states.put("Rhode Island", "us_rhode_island");
        states.put("South Carolina", "us_south_carolina");
        states.put("Tennessee", "us_tennessee");
        states.put("Texas", "us_texas");
        states.put("Utah", "us_utah");
        states.put("Washington", "us_washington");
        states.put("Wisconsin", "us_wisconsin");
        states.put("Wyoming", "us_wyoming");

        // populate cities
        cities = new HashMap<>();
        // Alabama
        List<String> alabamaCities = new LinkedList<>();
        alabamaCities.add("birmingham");
        alabamaCities.add("montgomery");
        cities.put("Alabama", alabamaCities);
        // Arizona
        List<String> arizonaCities = new LinkedList<>();
        arizonaCities.add("chandler");
        arizonaCities.add("mesa");
        arizonaCities.add("phoenix");
        arizonaCities.add("tucson");
        cities.put("Arizona", arizonaCities);
        // Arkansas
        List<String> arkansasCities = new LinkedList<>();
        arkansasCities.add("jacksonville");
        cities.put("Arkansas", arkansasCities);
        // California
        List<String> californiaCities = new LinkedList<>();
        californiaCities.add("compton");
        californiaCities.add("san_francisco");
        californiaCities.add("hayward");
        californiaCities.add("san_diego");
        californiaCities.add("oakland");
        californiaCities.add("los_angeles");
        californiaCities.add("sacramento");
        californiaCities.add("san_jose");
        californiaCities.add("bakersfield");
        californiaCities.add("cypress");
        californiaCities.add("escondido");
        californiaCities.add("long_beach");
        californiaCities.add("anaheim");
        californiaCities.add("panorama_city");
        californiaCities.add("san_bernardino");
        californiaCities.add("chula_vista");
        californiaCities.add("victorville");
        californiaCities.add("stockton");
        californiaCities.add("corona");
        californiaCities.add("fremont");
        californiaCities.add("garden_grove");
        californiaCities.add("hemet");
        cities.put("California", californiaCities);
        // Colorado
        List<String> coloradoCities = new LinkedList<>();
        coloradoCities.add("denver");
        coloradoCities.add("louisville");
        coloradoCities.add("colorado_springs");
        coloradoCities.add("erie");
        cities.put("Colorado", coloradoCities);
        // Connecticut
        List<String> connecticutCities = new LinkedList<>();
        connecticutCities.add("waterbury");
        connecticutCities.add("bridgeport");
        connecticutCities.add("hartford");
        connecticutCities.add("new_haven");
        cities.put("Connecticut", connecticutCities);
        // Florida
        List<String> floridaCities = new LinkedList<>();
        floridaCities.add("fort_lauderdale");
        floridaCities.add("miami");
        floridaCities.add("orlando");
        floridaCities.add("tampa");
        floridaCities.add("st_petersburg");
        floridaCities.add("hialeah");
        floridaCities.add("kissimmee");
        floridaCities.add("pompano_beach");
        floridaCities.add("lakeland");
        floridaCities.add("west_palm_beach");
        floridaCities.add("tallahassee");
        floridaCities.add("cape_coral");
        floridaCities.add("homestead");
        floridaCities.add("lake_worth");
        floridaCities.add("hollywood");
        floridaCities.add("winter_haven");
        floridaCities.add("boynton_beach");
        floridaCities.add("largo");
        floridaCities.add("port_orange");
        cities.put("Florida", floridaCities);
        // Georgia
        List<String> georgiaCities = new LinkedList<>();
        georgiaCities.add("columbus");
        georgiaCities.add("atlanta");
        georgiaCities.add("fayetteville");
        georgiaCities.add("augusta");
        georgiaCities.add("macon");
        georgiaCities.add("canton");
        georgiaCities.add("decatur");
        cities.put("Georgia", georgiaCities);
        // Hawaii
        List<String> hawaiiCities = new LinkedList<>();
        hawaiiCities.add("honolulu");
        cities.put("Hawaii", hawaiiCities);
        // Illinois
        List<String> illinoisCities = new LinkedList<>();
        illinoisCities.add("chicago");
        illinoisCities.add("washington");
        illinoisCities.add("rockford");
        illinoisCities.add("danville");
        cities.put("Illinois", illinoisCities);
        // Indiana
        List<String> indianaCities = new LinkedList<>();
        indianaCities.add("indianapolis");
        indianaCities.add("terre_haute");
        cities.put("Indiana", indianaCities);
        // Kansas
        List<String> kansasCities = new LinkedList<>();
        kansasCities.add("kansas_city");
        cities.put("Kansas", kansasCities);
        // Kentucky
        List<String> kentuckyCities = new LinkedList<>();
        kentuckyCities.add("richmond");
        kentuckyCities.add("lexington");
        cities.put("Kentucky", kentuckyCities);
        // Louisiana
        List<String> louisianaCities = new LinkedList<>();
        louisianaCities.add("houma");
        louisianaCities.add("baton_rouge");
        louisianaCities.add("new_orleans");
        cities.put("Louisiana", louisianaCities);
        // Maryland
        List<String> marylandCities = new LinkedList<>();
        marylandCities.add("baltimore");
        marylandCities.add("columbia");
        cities.put("Maryland", marylandCities);
        // Massachusetts
        List<String> massachusettsCities = new LinkedList<>();
        massachusettsCities.add("springfield");
        massachusettsCities.add("reading");
        massachusettsCities.add("worcester");
        massachusettsCities.add("auburn");
        massachusettsCities.add("watertown");
        massachusettsCities.add("jamaica_plain");
        cities.put("Massachusetts", massachusettsCities);
        // Michigan
        List<String> michiganCities = new LinkedList<>();
        michiganCities.add("detroit");
        michiganCities.add("clifford");
        cities.put("Michigan", michiganCities);
        // Minnesota
        List<String> minnesotaCities = new LinkedList<>();
        minnesotaCities.add("buffalo");
        minnesotaCities.add("minneapolis");
        minnesotaCities.add("austin");
        cities.put("Minnesota", minnesotaCities);
        // Missouri
        List<String> missouriCities = new LinkedList<>();
        missouriCities.add("st_louis");
        missouriCities.add("lake_saint_louis");
        missouriCities.add("chesterfield");
        cities.put("Missouri", missouriCities);
        // Nebraska
        List<String> nebraskaCities = new LinkedList<>();
        nebraskaCities.add("omaha");
        nebraskaCities.add("grand_island");
        nebraskaCities.add("norfolk");
        cities.put("Nebraska", nebraskaCities);
        // Nevada
        List<String> nevadaCities = new LinkedList<>();
        nevadaCities.add("las_vegas");
        cities.put("Nevada", nevadaCities);
        // New Hampshire
        List<String> newHampshireCities = new LinkedList<>();
        newHampshireCities.add("portsmouth");
        cities.put("New Hampshire", newHampshireCities);
        // New Jersey
        List<String> newJerseyCities = new LinkedList<>();
        newJerseyCities.add("madison");
        newJerseyCities.add("clifton");
        newJerseyCities.add("paterson");
        newJerseyCities.add("jersey_city");
        cities.put("New Jersey", newJerseyCities);
        // New Mexico
        List<String> newMexicoCities = new LinkedList<>();
        newMexicoCities.add("albuquerque");
        cities.put("New Mexico", newMexicoCities);
        // New York
        List<String> newYorkCities = new LinkedList<>();
        newYorkCities.add("new_york");
        newYorkCities.add("brooklyn");
        newYorkCities.add("the_bronx");
        newYorkCities.add("rochester");
        newYorkCities.add("jamaica");
        newYorkCities.add("syracuse");
        newYorkCities.add("albany");
        newYorkCities.add("staten_island");
        newYorkCities.add("utica");
        newYorkCities.add("schenectady");
        newYorkCities.add("yonkers");
        newYorkCities.add("south_richmond_hill");
        newYorkCities.add("freeport");
        cities.put("New York", newYorkCities);
        // North Carolina
        List<String> northCarolinaCities = new LinkedList<>();
        northCarolinaCities.add("charlotte");
        northCarolinaCities.add("wilmington");
        northCarolinaCities.add("henderson");
        northCarolinaCities.add("durham");
        northCarolinaCities.add("high_point");
        northCarolinaCities.add("raleigh");
        northCarolinaCities.add("greensboro");
        cities.put("North Carolina", northCarolinaCities);
        // Ohio
        List<String> ohioCities = new LinkedList<>();
        ohioCities.add("cleveland");
        ohioCities.add("cincinnati");
        ohioCities.add("newark");
        ohioCities.add("dayton");
        ohioCities.add("warren");
        ohioCities.add("riverside");
        ohioCities.add("lancaster");
        ohioCities.add("aurora");
        ohioCities.add("akron");
        ohioCities.add("zanesville");
        ohioCities.add("marion");
        ohioCities.add("youngstown");
        ohioCities.add("toledo");
        cities.put("Ohio", ohioCities);
        // Oklahoma
        List<String> oklahomaCities = new LinkedList<>();
        oklahomaCities.add("oklahoma_city");
        cities.put("Oklahoma", oklahomaCities);
        // Oregon
        List<String> oregonCities = new LinkedList<>();
        oregonCities.add("portland");
        oregonCities.add("astoria");
        oregonCities.add("medford");
        cities.put("Oregon", oregonCities);
        // Pennsylvania
        List<String> pennsylvaniaCities = new LinkedList<>();
        pennsylvaniaCities.add("philadelphia");
        pennsylvaniaCities.add("pittsburgh");
        pennsylvaniaCities.add("allentown");
        pennsylvaniaCities.add("harrisburg");
        cities.put("Pennsylvania", pennsylvaniaCities);
        // Rhode Island
        List<String> rhodeIslandCities = new LinkedList<>();
        rhodeIslandCities.add("middletown");
        rhodeIslandCities.add("providence");
        cities.put("Rhode Island", rhodeIslandCities);
        // South Carolina
        List<String> southCarolinaCities = new LinkedList<>();
        southCarolinaCities.add("anderson");
        southCarolinaCities.add("greenwood");
        southCarolinaCities.add("spartanburg");
        southCarolinaCities.add("sumter");
        cities.put("South Carolina", southCarolinaCities);
        // Tennessee
        List<String> tennesseeCities = new LinkedList<>();
        tennesseeCities.add("memphis");
        tennesseeCities.add("nashville");
        cities.put("Tennessee", tennesseeCities);
        // Texas
        List<String> texasCities = new LinkedList<>();
        texasCities.add("houston");
        texasCities.add("san_antonio");
        texasCities.add("dallas");
        texasCities.add("el_paso");
        texasCities.add("fresno");
        texasCities.add("fort_worth");
        texasCities.add("greenville");
        texasCities.add("waco");
        cities.put("Texas", texasCities);
        // Utah
        List<String> utahCities = new LinkedList<>();
        utahCities.add("salt_lake_city");
        cities.put("Utah", utahCities);
        // Washington
        List<String> washingtonCities = new LinkedList<>();
        washingtonCities.add("seattle");
        washingtonCities.add("monroe");
        washingtonCities.add("arlington");
        cities.put("Washington", washingtonCities);
        // Wisconsin
        List<String> wisconsinCities = new LinkedList<>();
        wisconsinCities.add("milwaukee");
        wisconsinCities.add("delafield");
        cities.put("Wisconsin", wisconsinCities);
        // Wyoming
        List<String> wyomingCities = new LinkedList<>();
        wyomingCities.add("cheyenne");
        cities.put("Wyoming", wyomingCities);
    }

    public String getRandomEndpoint(String country, String user) {
        String username = "customer-" + user + "-cc-" + country.toLowerCase();
        // get random state
        Random generator = new Random();
        String stateKey = states.keySet()
                .stream()
                .skip(generator.nextInt(states.size()))
                .findFirst()
                .get();
        String state = states.get(stateKey);
        username += "-st-"+state;

        // get random city
        List<String> stateCities = cities.get(stateKey);
        String theCity = stateCities.get(generator.nextInt(stateCities.size()));
        username += "-city-"+theCity;

        log.info(username);
        return username;
    }

    public String getEndpointUser(String country, String state, String city, String user) {
        String username = "customer-" + user + "-cc-" + country.toLowerCase();

        if (StringUtils.hasLength(state) && !state.equalsIgnoreCase("any")) {
            username += "-st-"+states.get(state);
        }

        if (StringUtils.hasLength(city) && !city.equalsIgnoreCase("Any")) {
            username += "-city-"+city.toLowerCase().replace(" ", "_");
        }

        username += "-sessid-"+UUID.randomUUID().toString().split("-")[0]+"-sesstime-10";
        log.info(username);
        return username;
    }
}