import java.sql.*;
import java.util.*;

/**
 * Database is an interface to the college application database, it
 * uses JDBC to connect to a SQLite3 file.
 */
public class Database {

    /**
     * The database connection.
     */
    private Connection conn;

    /**
     * Creates the database interface object. Connection to the
     * database is performed later.
     */
    public Database() {
        conn = null;
    }

    /**
     * Opens a connection to the database, using the specified
     * filename (if we'd used a traditional DBMS, such as PostgreSQL
     * or MariaDB, we would have specified username and password
     * instead).
     */
    public boolean openConnection(String filename) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
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

    /* ================================== */
    /* --- insert your own code below --- */
    /* ===============================*== */

    public List<StudentInfo> getStudentInfo(String college, String major) {
        LinkedList<StudentInfo> found = new LinkedList<StudentInfo>();
        String query =
            "SELECT   s_id, s_name, gpa\n" +
            "FROM     students\n" +
            "JOIN     applications\n" +
            "USING    (s_id)\n" +
            "WHERE    c_name = ? AND major = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, college);
            ps.setString(2, major);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(StudentInfo.fromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;            
    }

    public List<ApplicationInfo> getApplicationInfo() {
        LinkedList<ApplicationInfo> found = new LinkedList<ApplicationInfo>();
        String query =
            "SELECT   c_name, major, count() AS cnt\n" +
            "FROM     applications\n" +
            "GROUP BY c_name, major\n" +
            "ORDER BY cnt DESC";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(ApplicationInfo.fromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;        
    }

    public void gradeFix(String college, double pct) {
        String stmt =
            "UPDATE students\n" +
            "SET    gpa = min(4, gpa * (1 + ?))\n" +
            "WHERE  s_id IN (\n" +
            "    SELECT DISTINCT s_id\n" +
            "    FROM            applications\n" +
            "    WHERE           c_name = ?\n" +
            ")";
        try (PreparedStatement ps = conn.prepareStatement(stmt)) {
            ps.setDouble(1, pct);
            ps.setString(2, college);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
ApplicationInfo
c_name -> college : String
major : String
cnt -> count : int
*/

class ApplicationInfo {

    public final String college;
    public final String major;
    public final int count;

    private ApplicationInfo  (String college, String major, int count) {
        this.college = college;
        this.major = major;
        this.count = count;
    }

    public static ApplicationInfo fromRS(ResultSet rs) throws SQLException {
        return new ApplicationInfo(rs.getString("c_name"), rs.getString("major"), rs.getInt("cnt"));
    }
}


/*
StudentInfo
s_id -> id : int
s_name -> name : String
gpa : double
*/

class StudentInfo {

    public final int id;
    public final String name;
    public final double gpa;

    private StudentInfo  (int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public static StudentInfo fromRS(ResultSet rs) throws SQLException {
        return new StudentInfo(rs.getInt("s_id"), rs.getString("s_name"), rs.getDouble("gpa"));
    }
}