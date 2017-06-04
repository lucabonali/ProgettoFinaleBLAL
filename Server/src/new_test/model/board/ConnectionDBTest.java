package new_test.model.board;

import main.model.board.ConnectionDB;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * @author Andrea
 * @author Luca
 */
public class ConnectionDBTest {
    private ResultSet resultSet;
    private ConnectionDB connectionDB;

    @Before
    public void setup() {
        connectionDB = new ConnectionDB();
    }

    @Test
    public void executeQuery() throws SQLException {
        resultSet = connectionDB.executeQuery("SELECT * FROM cards WHERE name = \"badessa\"");
        resultSet.next();
        assertEquals("badessa", resultSet.getString("name"));
    }

    @Test
    public void closeConnection() {
        connectionDB.closeConnection();
    }

}