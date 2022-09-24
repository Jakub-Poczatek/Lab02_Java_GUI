import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectToSQL {
    // The name of the MySQL account to use
    private final String userName = "root";

    // The password for the MySQL account
    private final String password = "";

    // The name of the computer running MySQL
    private final String serverName = "localhost";

    // The name of the table we are testing with
    private final String tableName = "JDBC_TEST";

    // Get a new database connection
    public Connection getConnection(int portNumber, String dbName) throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:mysql://" +
                        this.serverName + ":" + portNumber + "/" + dbName,
                connectionProps);
        return conn;
    }

    // Run an SQL command which does not return a recordset:
    public boolean executeUpdate(Connection conn, String command) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(command);
            return true;
        } finally {
            // This will run wether we throw an exception or not
            if (stmt != null) {stmt.close();}
        }
    }

    public void run() {
        // Connect to MySQL
        Connection conn = null;
        try {
            conn = this.getConnection(3306, "lab03");
            System.out.println("Connected to the database");
        } catch (SQLException e){
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }

        // Create a table
        try {
            String createString =
                    "CREATE TABLE " + this.tableName + " ( " +
                            "ID INTEGER NOT NULL, " +
                            "NAME varchar(40) NOT NULL, " +
                            "STREET varchar(40) NOT NULL, " +
                            "CITY varchar(40) NOT NULL, " +
                            "STATE char(2) NOT NULL, " +
                            "ZIP char(5), " +
                            "PRIMARY KEY (ID))";
            this.executeUpdate(conn, createString);
            System.out.println("Created a table");
        } catch (SQLException e) {
            System.out.print("ERROR: Could not create the table");
            e.printStackTrace();
        }

        // Drop the table
        /*try {
            String dropString = "DROP TABLE " + this.tableName;
            this.executeUpdate(conn, dropString);
            System.out.print("Dropped the table");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not drop the table");
            e.printStackTrace();
            return;
        }*/
    }

    // Connect to the DB and do some stuff
    public static void main(String[] args) {
        ConnectToSQL app = new ConnectToSQL();
        app.run();
    }
}
