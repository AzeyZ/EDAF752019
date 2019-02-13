import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Database is an interface to the college application database, it uses JDBC to
 * connect to a SQLite3 file.
 */
public class Database {

	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Creates the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Opens a connection to the database, using the specified filename (if we'd
	 * used a traditional DBMS, such as PostgreSQL or MariaDB, we would have
	 * specified username and password instead).
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
		String query = "SELECT   s_id, s_name, gpa\n" + "FROM     students\n" + "JOIN     applications\n"
				+ "USING    (s_id)\n" + "WHERE    c_name = ? AND major = ?";
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

	public List<TheaterInfo> getApplicationInfo() {
		LinkedList<TheaterInfo> found = new LinkedList<TheaterInfo>();
		String query = "SELECT   c_name, major, count() AS cnt\n" + "FROM     applications\n"
				+ "GROUP BY c_name, major\n" + "ORDER BY cnt DESC";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				found.add(TheaterInfo.fromRS(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return found;
	}

	public void gradeFix(String college, double pct) {
		String stmt = "UPDATE students\n" + "SET    gpa = min(4, gpa * (1 + ?))\n" + "WHERE  s_id IN (\n"
				+ "    SELECT DISTINCT s_id\n" + "    FROM            applications\n"
				+ "    WHERE           c_name = ?\n" + ")";
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
 * ApplicationInfo c_name -> college : String major : String cnt -> count : int
 */
class ScreeningInfo {




	public final Time screening_time;
	public final Date screening_date;
	public final int production_year;
	public final String movie_name;
	public final String theater_name;

	public ScreeningInfo(Time screening_time, Date screening_date, int production_year, String movie_name,
			String theater_name) {
		super();
		this.screening_time = screening_time;
		this.screening_date = screening_date;
		this.production_year = production_year;
		this.movie_name = movie_name;
		this.theater_name = theater_name;
	}


	public static ScreeningInfo fromRS(ResultSet rs) throws SQLException {
		return new ScreeningInfo(rs.getTime("screening_time"), rs.getDate("screening_date"),
				rs.getInt("production_year"), rs.getString("movie_name"), rs.getString("theater_name"));
	}
}

class CustomerInfo {

	public final String user_name;
	public final String full_name;
	public final String password;

	public CustomerInfo(String user_name, String full_name, String password) {
		this.user_name = user_name;
		this.full_name = full_name;
		this.password = password;
	}

	public static CustomerInfo fromRS(ResultSet rs) throws SQLException {
		return new CustomerInfo(rs.getString("user_name"), rs.getString("full_name"), rs.getString("password"));
	}
}

class TicketInfo {

	public final int ticket_id;
	public final String user_name;
	public final Time screening_time;
	public final Date screening_date;
	public final String theater_name;

	public TicketInfo(int ticket_id, String user_name, Time screening_time, Date screening_date, String theater_name) {

		this.ticket_id = ticket_id;
		this.user_name = user_name;
		this.screening_time = screening_time;
		this.screening_date = screening_date;
		this.theater_name = theater_name;
	}

	public static TicketInfo fromRS(ResultSet rs) throws SQLException {
		return new TicketInfo(rs.getInt("ticket_id"), rs.getString("user_name"), rs.getTime("screening_time"),
				rs.getDate("screening_date"), rs.getString("theater_name"));
	}
}

class TheaterInfo {

	public final String theather_name;
	public final int capacity;

	public TheaterInfo(String theather_name, int capacity) {

		this.theather_name = theather_name;
		this.capacity = capacity;
	}

	public static TheaterInfo fromRS(ResultSet rs) throws SQLException {
		return new TheaterInfo(rs.getString("theater_name"), rs.getInt("capacity"));
	}
}

class MovieInfo {

	public final String movie_name;
	public final int production_year;
	public final int playtime;
	public final String imdb_key;

	public MovieInfo(String movie_name, int year, int playtime, String imdb_key) {
		super();
		this.movie_name = movie_name;
		this.production_year = year;
		this.playtime = playtime;
		this.imdb_key = imdb_key;
	}

	public static MovieInfo fromRS(ResultSet rs) throws SQLException {
		return new MovieInfo(rs.getString("movie_name"), rs.getInt("production_year"), rs.getInt("playtime"),
				rs.getString("imdb_key"));
	}
}