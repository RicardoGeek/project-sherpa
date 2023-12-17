package runners;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import providers.BrowserProvider;
import utils.Utils;

@Slf4j
public class TouchProcessor extends Thread {
    private final BrowserProvider browserProvider;

    private String url;

    public TouchProcessor() {
        browserProvider = new BrowserProvider();
    }

    @Override
    public void run() {
        WebDriver driver = null;
        try {
            log.info("Touching: " + url);
            driver = browserProvider.getHeadlessBrowserNoProxy();
            driver.get(url);

            int randomTime = Utils.getRandomNumber(120, 160);
            Thread.sleep(randomTime * 1000L);

            log.info("CLOSING TOUCH!");
            driver.quit();
        } catch (Exception ex) {
            String reason = ex.getMessage().split("\n")[0];
            log.info("____TOUCH FAILED____");
            log.info(reason);
            log.info("____________________");

            driver.quit();

            // Start again -.-"
            TouchProcessor p = new TouchProcessor();
            p.start();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
