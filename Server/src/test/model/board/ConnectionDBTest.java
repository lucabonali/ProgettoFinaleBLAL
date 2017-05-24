package test.model.board;

import main.model.board.ConnectionDB;
import org.junit.Test;

import java.sql.ResultSet;

/**
 * @author lampa
 */
public class ConnectionDBTest {
    @Test
    public void connection() {
        ConnectionDB c = new ConnectionDB();
    }

    @Test
    public void executeQuery() throws Exception {
        ConnectionDB c = new ConnectionDB();
        ResultSet res = c.executeQuery("SELECT * FROM cards WHERE type = \"character\"");
    }

    @Test
    public void closeConnection() throws Exception {
    }

}