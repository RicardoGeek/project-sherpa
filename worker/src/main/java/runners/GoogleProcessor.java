package runners;

import dao.ProcessPool;
import dao.ProcessPoolDao;
import dao.ProxyDetails;
import dao.ProxyDetailsDao;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import providers.BrowserProvider;
import providers.ProxyProvider;
import providers.RegionProvider;
import simulation.Simulator;
import utils.Utils;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

@Slf4j
public class GoogleProcessor extends Thread {
    private final BrowserProvider browserProvider;
    private final ProxyProvider proxyProvider;
    private final RegionProvider regionProvider;
    private final Simulator simulator;

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
        simulator = new Simulator();
    }

    @Override
    public void run() {
        WebDriver driver = null;
        BrowserMobProxyServer proxy = null;
        try {
            proxy = proxyProvider.getProxy(
                    this.proxyHost,
                    this.proxyPort,
                    this.proxyUsername,
                    this.proxyPassword
            );

            driver = browserProvider.getDefaultBrowser("Desktop", this.headless, proxy);

            log.info("---------STEP 1: SEARCHING GOOGLE---------");
            driver.get("https://google.com");
            sendTextWithSimulator(driver, "q", this.searchTerm, true);
            while (!driver.getTitle().contains(this.searchTerm)) {
                Thread.sleep(100);
            }
            scanLinksAndClick(driver, this.targetPage);

            waitComplete(driver);
            log.info("Target page loaded");
            log.info("---------STEP 2: LOCATE IFRAME AND CLICK---------");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

            String prevUrl = driver.getCurrentUrl();
            locateIframesAndClick(driver);
            String newUrl = driver.getCurrentUrl();

            if (!prevUrl.equals(newUrl)) {
                log.info("sponsored page: " + driver.getCurrentUrl());
                // TODO: register target page
                pageEngage(driver);
            }

            driver.quit();
            proxy.stop();

        } catch (Exception ex) {
            String reason = ex.getMessage().split("\n")[0];
            log.info(reason);

            boolean isOnSponsored = false;
            if(driver != null && proxy != null) {
                isOnSponsored = driver.getCurrentUrl().contains(this.targetPage);
                driver.quit();
                proxy.stop();
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

    private void pageEngage(WebDriver driver) throws InterruptedException {
        Thread.sleep(5000);
        waitComplete(driver);

        scanLinksAndClickFirst(driver); // engage in the sponsor
        Thread.sleep(50000);
    }


    private void waitComplete(final WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60L));
        wait.until(wd -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    private void scanLinksAndClick(WebDriver driver, String searchable) throws InterruptedException {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            String anchor = link.getText();
            if(anchor.isBlank() || anchor.isEmpty()) continue;

            String url = link.getAttribute("href");

            if (url != null && url.contains(searchable) ) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);

                int clickDelay = Utils.getRandomNumber(100, 150);
                Thread.sleep(clickDelay);
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-"+clickDelay+")");
                log.info("Clicking link: " + url);
                link.click();
                break;
            }
        }
    }

    private void scanLinksAndClickFirst(WebDriver driver) throws InterruptedException {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        log.info("FOUND " + links.size() + " FUCKING LINKS IN THE FUCKING IFRAME");
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href == null || href.isEmpty() || href.equals("#")) continue;

            int clickDelay = Utils.getRandomNumber(1000, 1500);
            Thread.sleep(clickDelay);

            String current = driver.getCurrentUrl();
            log.info("Clicking link: " + href);
            int w = link.getSize().getWidth();
            int h = link.getSize().getHeight();
            int t = w + h;
            if(!link.isDisplayed() || !link.isEnabled() || t <= 0) {
                log.info("stoopid link not interactable... maybe?");
                continue;
            }

            link.click();

            int maxTries = 5;
            while (current.equals(driver.getCurrentUrl())) {
                if(maxTries<=0) break;
                //FUCKING NAVIGATE GOD DAMNIT
                Thread.sleep(1000);
                log.info("WAITING FOR FUCKING NAVIGATION");
                maxTries--;
            }

            if(!current.equals(driver.getCurrentUrl())) break;
        }
    }

    private void locateIframesAndClick(WebDriver driver) throws InterruptedException {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        if (iframes.size() > 0) {
            log.info(iframes.size() + " iframes found");
            for (WebElement iframe : iframes) {
                if(iframe.getSize().getHeight() <= 0) continue;

               if(iframe.getAttribute("src").contains("googleads")) {
                   log.info("found google ads frame");

                   int waitRetries = 3;
                   while(!iframe.isDisplayed()) {
                       if(waitRetries<0) break;
                       log.info("Iframe not visible yet");
                       Thread.sleep(1000);
                       waitRetries--;
                   }
                   if(waitRetries<0) continue;

                   driver.switchTo().frame(iframe);

                   // wait for the fucking frame
                   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60L));
                   wait.until(wd -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

                   scanLinksAndClickFirst(driver);
                   break;
               }
            }
        } else {
            log.info("No ads found :C");
        }
        log.info("iframe process ended.... back to studio");
    }

    private void sendTextWithSimulator(WebDriver driver, String inputName, String text, boolean doEnter) throws InterruptedException {
        simulator.sendKeysWithDelay(driver.findElement(By.name(inputName)), text);
        if(doEnter) {
            int sleepTimer = Utils.getRandomNumber(300, 400);
            Thread.sleep(sleepTimer);
            driver.findElement(By.name(inputName)).sendKeys(Keys.ENTER);
        }

        log.info("Text query sent");
    }

}
