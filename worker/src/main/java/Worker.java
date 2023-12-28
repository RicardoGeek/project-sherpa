import config.FlywayConfig;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import runners.GoogleProcessor;
import runners.SponsoredLinkProcessor;
import runners.TouchProcessor;

import java.sql.SQLException;

public class Worker {
    public static void main(String[] args) {
        String type = args[0];
        System.out.println(type);
        switch (type) {
            case "1":
                TouchProcessor touchProcessor = new TouchProcessor();
                touchProcessor.setUrl("https://compasstrader.com");
                touchProcessor.start();
                break;
            case "2":
                GoogleProcessor.spawn();
                break;
            case "3":
                SponsoredLinkProcessor.spawn();
                break;
            case "99":
                initializeFlyway();
                break;
            default:
                System.out.println("wtf parameter");
                break;
        }
    }

    private static void initializeFlyway() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FlywayConfig.class)) {
            Flyway flyway = context.getBean(Flyway.class);
            flyway.migrate();
        }
    }
}
