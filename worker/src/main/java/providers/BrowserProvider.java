package providers;

import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

@Slf4j
public class BrowserProvider {
    public WebDriver getDefaultBrowser(String userAgentType, boolean headless, BrowserMobProxyServer proxy) {
        UserAgentProvider userAgentProvider = new UserAgentProvider();
        ChromeOptions chromeOptions = new ChromeOptions();
        String userAgent = userAgentProvider.getRandomUserAgent(userAgentType);
        log.info(userAgent);

        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.addArguments("--user-agent="+userAgent);

        if(headless) {
            chromeOptions.addArguments("--display=:99");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--disable-software-rasterizer");
            chromeOptions.addArguments("--remote-debugging-port=9222");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
        }

        chromeOptions.setAcceptInsecureCerts(true);

        chromeOptions.setCapability(CapabilityType.PROXY, ClientUtil.createSeleniumProxy(proxy));

        return new ChromeDriver(chromeOptions);
    }

    public WebDriver getHeadlessBrowserNoProxy() {
        UserAgentProvider userAgentProvider = new UserAgentProvider();
        ChromeOptions chromeOptions = new ChromeOptions();
        String userAgent = userAgentProvider.getRandomUserAgent();
        log.info(userAgent);

        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.addArguments("--user-agent="+userAgent);
        chromeOptions.addArguments("--display=:99");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-software-rasterizer");
        chromeOptions.addArguments("--remote-debugging-port=9222");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");

        chromeOptions.setAcceptInsecureCerts(true);

        return new ChromeDriver(chromeOptions);
    }
}
