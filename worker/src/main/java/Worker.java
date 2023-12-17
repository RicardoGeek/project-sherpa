import runners.GoogleProcessor;
import runners.TouchProcessor;

public class Worker {
    public static void main(String[] args)  {
        String type = args[0];
        System.out.println(type);
        switch (type) {
            case "1":
                TouchProcessor touchProcessor = new TouchProcessor();
                touchProcessor.setUrl("https://compasstrader.com");
                touchProcessor.start();
                break;
            case "2":
                GoogleProcessor googleProcessor = new GoogleProcessor();
                googleProcessor.setHeadless(true);
                googleProcessor.setProxyHost("gw.dataimpulse.com");
                googleProcessor.setProxyPort(1000);
                googleProcessor.setProxyUsername("2b0367e87a0ad278eb1b");
                googleProcessor.setProxyPassword("58c189303c5bee20");
                googleProcessor.setSearchTerm("parabolic sar formula compasstrader");
                googleProcessor.setTargetPage("compasstrader.com");
                googleProcessor.start();
                break;
            default:
                System.out.println("wtf parameter");
                break;
        }
    }
}
