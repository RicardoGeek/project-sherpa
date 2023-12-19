package dao;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ProxyDetailsDao {
    private DbAccess dbAccess;

    public ProxyDetailsDao() {
        dbAccess = new DbAccess();
    }

    public ProxyDetails getBest() {
        try {
            List<ProxyDetails> proxies = dbAccess.executeProxySelect("SELECT * FROM proxies WHERE quality = 'BEST'");

            return proxies.get(0);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return null;
        }

    }
}
