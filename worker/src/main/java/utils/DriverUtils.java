package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import simulation.Simulator;

import java.time.Duration;
import java.util.List;

@Slf4j
public class DriverUtils {
    private final Simulator simulator;

    public DriverUtils() {
        this.simulator = new Simulator();
    }

    public void pageEngage(WebDriver driver) throws InterruptedException {
        Thread.sleep(5000);
        waitComplete(driver);

        scanLinksAndClickFirst(driver); // engage in the sponsor
        Thread.sleep(50000);
    }


    public void waitComplete(final WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60L));
        wait.until(wd -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void scanLinksAndClick(WebDriver driver, String searchable) throws InterruptedException {
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

    public void scanLinksAndClickFirst(WebDriver driver) throws InterruptedException {
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

    public void locateIframesBySrcAndClick(WebDriver driver, String src) throws InterruptedException {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        if (iframes.size() > 0) {
            log.info(iframes.size() + " iframes found");
            for (WebElement iframe : iframes) {
                if(iframe.getSize().getHeight() <= 0) continue;

                if(iframe.getAttribute("src").contains(src)) {
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
        log.info("iframe process ended, returning...");
    }

    public void locateIframesAndClick(WebDriver driver) throws InterruptedException {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        if (iframes.size() > 0) {
            log.info(iframes.size() + " iframes found");
            for (WebElement iframe : iframes) {
                log.info("detected size: "  + iframe.getSize().toString());
                if(iframe.getSize().toString().equals("(0, 0)")) continue;
                log.info("iframe src = " + iframe.getAttribute("src"));

                if(iframe.getAttribute("src").isEmpty() ||
                    iframe.getAttribute("src").equals("about:blank")) {

                    driver.switchTo().frame(iframe);

                    // wait for the fucking frame
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60L));
                    wait.until(wd -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

                    break;
                }
            }
        } else {
            log.info("No ads found :C");
        }
        log.info("iframe process ended, returning...");
    }

    private void treeSizes(WebElement element) {
        log.info(element.getSize().toString());
        treeSizes(element.findElement(By.xpath("*")));
    }

    public void sendTextWithSimulator(WebDriver driver, String inputName, String text, boolean doEnter) throws InterruptedException {
        simulator.sendKeysWithDelay(driver.findElement(By.name(inputName)), text);
        if(doEnter) {
            int sleepTimer = Utils.getRandomNumber(300, 400);
            Thread.sleep(sleepTimer);
            driver.findElement(By.name(inputName)).sendKeys(Keys.ENTER);
        }

        log.info("Text query sent");
    }
}
