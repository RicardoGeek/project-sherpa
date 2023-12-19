package dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Slf4j
public class ProcessPoolDao {

    private DbAccess dbAccess;

    public ProcessPoolDao() {
        dbAccess = new DbAccess();
    }

    public ProcessPool getNext() {
        try {
            List<ProcessPool> rs = dbAccess.executeProcessSelect("SELECT * FROM process_pool ORDER BY sink_date ASC LIMIT 1");

            return rs.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return null;
        }
    }

    public void sink(int id) {
        String query = "UPDATE process_pool SET sink_date = NOW() WHERE id = " + id;
        try {
            dbAccess.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}
