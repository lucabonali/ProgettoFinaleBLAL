package board;

import java.sql.*;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che si occupa della connessione e interazione
 * con il database delle carte
 */
public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/NOME_DEL_DB";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME_DB = "root";
    private static final String PASSWORD_DB = "";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ConnectionDB() {
        //inizializza la connessione
//        try {
//            Class.forName(JDBC_DRIVER);
//            connection = DriverManager.getConnection(URL, USERNAME_DB, PASSWORD_DB);
//            statement = connection.createStatement();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * metodo che mi esegue la query al database
     * @param query query da eseguire
     * @return il result set, risultato dell'interrogazione al db
     */
    public ResultSet executeQuery(String query) {
        //da implementare l'interrogazione al db
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     metodo che mi chiude la connessione
     * @throws SQLException in caso dire errori col db
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
