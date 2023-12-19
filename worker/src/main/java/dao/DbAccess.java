package dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbAccess {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/sherpa";
        Class.forName(driver);
        return DriverManager.getConnection(url, "root", "change-me");
    }

    public List<ProcessPool> executeProcessSelect(String query) throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        List<ProcessPool> retVal = new LinkedList<>();

        while (rs.next()) {
            retVal.add( ProcessPool.builder()
                    .id(rs.getInt("id"))
                    .searchTerm(rs.getString("search_term"))
                    .domain(rs.getString("domain"))
                    .sinkDate(rs.getTimestamp("sink_date"))
                    .build() );
        }

        st.close();
        rs.close();
        return retVal;
    }

    public List<ProxyDetails> executeProxySelect(String query) throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        List<ProxyDetails> retVal = new LinkedList<>();

        while (rs.next()) {
            retVal.add( ProxyDetails.builder()
                    .id(rs.getInt("id"))
                    .host(rs.getString("host"))
                    .port(rs.getInt("port"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                            .provider(rs.getString("provider"))
                    .build() );
        }

        st.close();
        rs.close();
        return retVal;
    }

    public int executeUpdate(String update) throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(update);

        return ps.executeUpdate();
    }
}
