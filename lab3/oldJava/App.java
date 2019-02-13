package aplications.rest;
import spark.*;
import static spark.Spark.*;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;

public class App {

    public static void main(String[] args) {
        Database db = new Database("lab3.sqlite");
        port(4567);
// 	before("/*", (req, res) -> appl.log(req.splat()[0]));
//        get("/students", (req, res) -> db.getStudents(req, res));
//        post("/students", (req, res) -> db.postStudent(req, res));
//        get("/students/:id", (req, res) -> db.getStudent(req, res, req.params(":id")));
    }

//    public static void main(String[] args) {
//        new App().run();
//    }

//    Database db = new Database();

//    void run() {
//        db.openConnection("lab3.sqlite");
//        int år = 2016;
//        for (MovieInfo movies : db.getMovieInfo("Moonlight", år, (int)111, "tt4975722")) {
//            System.out.println(movies.movie_name);
//        }
//
//        System.out.println("Most popular colleges/majors");
//        for (ApplicationInfo ai : db.getApplicationInfo()) {
//            System.out.println(ai.count + ":" + ai.college + "/" + ai.major);
//        }
//
//        db.gradeFix("Stanford", 0.04);
    }

class Database {
    /**
     * The database connection.
     */
    private Connection conn;

    /**
     * Creates the database interface object. Connection to the
     * database is performed later.
     */
    public Database(String filename) {
        openConnection(filename);
    }

    /**
     * Opens a connection to the database, using the specified
     * filename (if we'd used a traditional DBMS, such as PostgreSQL
     * or MariaDB, we would have specified username and password
     * instead).
     */
    public boolean openConnection(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection to the database has been established
     * 
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }
}

/**
 * Auxiliary class for automatically translating a ResultSet to JSON
 */
class JSONizer {

    public static String toJSON(ResultSet rs, String name) throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSetMetaData meta = rs.getMetaData();
        boolean first = true;
        sb.append("{\n");
        sb.append("  \"" + name + "\": [\n");
        while (rs.next()) {
            if (!first) {
                sb.append(",");
                sb.append("\n");
            }
            first = false;
            sb.append("    {");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String label = meta.getColumnLabel(i);
                String value = getValue(rs, i, meta.getColumnType(i));
                sb.append("\"" + label + "\": " + value);
                if (i < meta.getColumnCount()) {
                    sb.append(", ");
                }
            }
            sb.append("}");
        }
        sb.append("\n");
        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }
	
    private static String getValue(ResultSet rs, int i, int columnType) throws SQLException {
        switch (columnType) {
        case java.sql.Types.INTEGER:
            return String.valueOf(rs.getInt(i));
        case java.sql.Types.REAL:
        case java.sql.Types.DOUBLE:
        case java.sql.Types.FLOAT:
            return String.valueOf(rs.getDouble(i));
        default:
            return "\"" + rs.getString(i) + "\"";
        }
    }
}
