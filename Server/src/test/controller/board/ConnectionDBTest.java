package test.controller.board;

import main.model.board.ConnectionDB;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lampa
 */
class ConnectionDBTest {

    @Test
    void executeQuery() throws SQLException {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = connectionDB.executeQuery("SELECT * FROM cards");
        assertTrue(rs != null);
    }

    @Test
    void closeConnection() {
    }

}