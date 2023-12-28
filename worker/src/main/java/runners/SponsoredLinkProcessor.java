package runners;

import dao.ProcessPool;
import dao.ProcessPoolDao;
import dao.ProxyDetails;
import dao.ProxyDetailsDao;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import providers.BrowserProvider;
import providers.ProxyProvider;
import providers.RegionProvider;
import utils.DriverUtils;
import utils.ProcessUtils;

@Slf4j
public class SponsoredLinkProcessor extends Thread {
    private final BrowserProvider browserProvider;
    private final ProxyProvider proxyProvider;
    private final RegionProvider regionProvider;
    private final DriverUtils driverUtils;

    // proxy configs for process
    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;
    private boolean headless;

    public SponsoredLinkProcessor() {
        browserProvider = new BrowserProvider();
        proxyProvider = new ProxyProvider();
        regionProvider = new RegionProvider();
        driverUtils = new DriverUtils();
    }

    @Override
    public void run() {
        WebDriver driver = null;
        BrowserMobProxyServer proxy = null;
        try {
            // check previous dead processes
            /*ProcessUtils.kill("java");
            ProcessUtils.kill("chrome");
            ProcessUtils.kill("chromedriver");*/

            proxy = proxyProvider.getProxy(
                    this.proxyHost,
                    this.proxyPort,
                    this.proxyUsername,
                    this.proxyPassword
            );

            driver = browserProvider.getDefaultBrowser("Desktop", this.headless, proxy);

            driver.get("https://allmetalmixtapes.com");

            driverUtils.waitComplete(driver);

            log.info("breathing slowly for 10 seconds");
            Thread.sleep(10000);
            log.info("ok finished breathing");

            String prevUrl = driver.getCurrentUrl();
            driverUtils.locateIframesAndClick(driver);
            String newUrl = driver.getCurrentUrl();

            if (!prevUrl.equals(newUrl)) {
                log.info("sponsored page: " + driver.getCurrentUrl());
                // TODO: register target page
                driverUtils.pageEngage(driver);
            } else {
                log.info("unable to click iframe, clicking link instead");
                driver.findElement(By.linkText("sponsors")).click();

                log.info("breathing slowly for 10 seconds");
                Thread.sleep(10000);
                log.info("ok finished breathing");

                driverUtils.pageEngage(driver);
            }

            proxy.stop();
            driver.quit();
        } catch (Exception ex) {
            String reason = ex.getMessage().split("\n")[0];
            log.info(reason);

            if (proxy != null) proxy.stop();
            if (driver != null) {
                driver.quit();
            }

            SponsoredLinkProcessor.spawn();
        }
    }

    public static void spawn() {
        // get process
        ProcessPoolDao processPoolDao = new ProcessPoolDao();
        ProcessPool p = processPoolDao.getNext();
        // get BEST proxy
        ProxyDetailsDao pdd = new ProxyDetailsDao();
        ProxyDetails pd = pdd.getBest();

        SponsoredLinkProcessor slp = new SponsoredLinkProcessor();
        slp.setHeadless(false);
        slp.setProxyHost(pd.getHost());
        slp.setProxyPort(pd.getPort());
        slp.setProxyUsername(pd.getUsername(), pd.getProvider());
        slp.setProxyPassword(pd.getPassword());
        slp.start();
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
                this.proxyUsername = proxyUsername ;
        }
        log.info(this.proxyUsername);
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }
}
