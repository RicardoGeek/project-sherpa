package runners;

import dao.ProcessPool;
import dao.ProcessPoolDao;
import dao.ProxyDetails;
import dao.ProxyDetailsDao;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.*;
import providers.BrowserProvider;
import providers.ProxyProvider;
import providers.RegionProvider;
import utils.DriverUtils;
import utils.ProcessUtils;

@Slf4j
public class GoogleProcessor extends Thread {
    private final BrowserProvider browserProvider;
    private final ProxyProvider proxyProvider;
    private final RegionProvider regionProvider;
    private final DriverUtils du;

    // proxy configs for process
    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    // search terms, domain
    private String searchTerm;
    private String targetPage;
    private boolean headless;

    public GoogleProcessor() {
        browserProvider = new BrowserProvider();
        proxyProvider = new ProxyProvider();
        regionProvider = new RegionProvider();
        du = new DriverUtils();
    }

    @Override
    public void run() {
        WebDriver driver = null;
        BrowserMobProxyServer proxy = null;
        try {
            // check previous dead processes
            ProcessUtils.kill("java");
            ProcessUtils.kill("chrome");
            ProcessUtils.kill("chromedriver");

            proxy = proxyProvider.getProxy(
                    this.proxyHost,
                    this.proxyPort,
                    this.proxyUsername,
                    this.proxyPassword
            );

            driver = browserProvider.getDefaultBrowser("Desktop", this.headless, proxy);

            log.info("---------STEP 1: SEARCHING GOOGLE---------");
            driver.get("https://google.com");
            du.sendTextWithSimulator(driver, "q", this.searchTerm, true);

            while (!driver.getTitle().contains(this.searchTerm)) {
                Thread.sleep(100);
            }

            du.scanLinksAndClick(driver, this.targetPage);

            du.waitComplete(driver);
            log.info("Target page loaded");

            log.info("breathing slowly for 10 seconds");
            Thread.sleep(10000);
            log.info("ok finished breathing");

            log.info("---------STEP 2: LOCATE IFRAME AND CLICK---------");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

            String prevUrl = driver.getCurrentUrl();
            du.locateIframesBySrcAndClick(driver, "googleads");
            String newUrl = driver.getCurrentUrl();

            if (!prevUrl.equals(newUrl)) {
                log.info("sponsored page: " + driver.getCurrentUrl());
                // TODO: register target page
                du.pageEngage(driver);
            }

            driver.quit();
            proxy.stop();

        } catch (Exception ex) {
            String reason = ex.getMessage().split("\n")[0];
            log.info(reason);

            if(proxy != null) proxy.stop();

            boolean isOnSponsored = false;
            if(driver != null) {
                isOnSponsored = driver.getCurrentUrl().contains(this.targetPage);
                driver.quit();
            }

            if(!isOnSponsored) {
                spawn();
            }
        }
    }

    public static void spawn() {
        // get process
        ProcessPoolDao processPoolDao = new ProcessPoolDao();
        ProcessPool p = processPoolDao.getNext();
        // get BEST proxy
        ProxyDetailsDao pdd = new ProxyDetailsDao();
        ProxyDetails pd = pdd.getBest();

        GoogleProcessor googleProcessor = new GoogleProcessor();
        googleProcessor.setHeadless(false);
        googleProcessor.setProxyHost(pd.getHost());
        googleProcessor.setProxyPort(pd.getPort());
        googleProcessor.setProxyUsername(pd.getUsername(), pd.getProvider());
        googleProcessor.setProxyPassword(pd.getPassword());
        googleProcessor.setSearchTerm(p.getSearchTerm());
        googleProcessor.setTargetPage(p.getDomain());
        googleProcessor.start();
        processPoolDao.sink(p.getId()); // put it at the end of the list
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUsername(String proxyUsername, String brand) {
        switch (brand) {
            case "oxy":
                this.proxyUsername = regionProvider.getRandomEndpoint("us", "ricardogeek")+"-sessid-"+proxyProvider.getRandomSessionId()+"-sesstime-10";
                break;
            case "dataimpulse":
                this.proxyUsername = proxyUsername + "__cr.us";
        }
        log.info(this.proxyUsername);
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

}
