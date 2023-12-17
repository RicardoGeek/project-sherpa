package simulation;

import org.openqa.selenium.WebElement;

public class Simulator {
    public void sendKeysWithDelay(WebElement input, String text) throws InterruptedException {
        for(int i =  0; i < text.length(); i++) {
            input.sendKeys(String.valueOf(text.charAt(i)));
            int millisDelay = getRandomNumber(10, 200);
            Thread.sleep(millisDelay);
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
