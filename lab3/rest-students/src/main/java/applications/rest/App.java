/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package applications.rest;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import spark.*;
import static spark.Spark.*;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class App {

    public static void main(String[] args) {
        Database db = new Database("movies.db");
        port(7007);

        get("/ping", (req, res) -> db.getPing(res));
        post("/reset", (req, res) -> db.resetTable(req, res));
	post("/performances", (req, res) -> db.addPerformance(req, res));
	post("/tickets", (req, res) -> db.addTicket(req, res));
	get("/movies", (req, res)-> db.getMovies(req, res));
	get("/performances", (req, res) -> db.getPerformances(req, res));
//"application/json", (req, res) -> db.getMovies(req, res));
	get("/movies/:imdb-key", (req, res) -> db.getMovie(req, res, req.params(":imdb-key")));
	get("/customers/:username/tickets", (req, res) -> db.getCustomer(req, res, req.params(":username")));
    }
}


class Database {
    /**
     * The database connection.
     */
    private Connection conn;
    private PasswordHashGenerator passGen;	

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

	/* ================================== */
	/* --- insert your own code below --- */
	/* ===============================*== */

	public List<MovieInfo> getMovieInfo(String movie_name, int production_year, int playtime, String imdb_key) {
		LinkedList<MovieInfo> found = new LinkedList<MovieInfo>();
		String query = 
		"SELECT   *\n" + 
		"FROM     movies\n";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, movie_name);
			ps.setInt(2, production_year);
			ps.setInt(3, playtime);
			ps.setString(4, imdb_key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				found.add(MovieInfo.fromRS(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return found;
	}

//	public List<TheaterInfo> getApplicationInfo() {
//		LinkedList<TheaterInfo> found = new LinkedList<TheaterInfo>();
//		String query = "SELECT   c_name, major, count() AS cnt\n" + "FROM     applications\n"
//				+ "GROUP BY c_name, major\n" + "ORDER BY cnt DESC";
//		try (PreparedStatement ps = conn.prepareStatement(query)) {
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				found.add(TheaterInfo.fromRS(rs));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return found;
//	}
//
//	public void gradeFix(String college, double pct) {
//		String stmt = "UPDATE students\n" + "SET    gpa = min(4, gpa * (1 + ?))\n" + "WHERE  s_id IN (\n"
//				+ "    SELECT DISTINCT s_id\n" + "    FROM            applications\n"
//				+ "    WHERE           c_name = ?\n" + ")";
//		try (PreparedStatement ps = conn.prepareStatement(stmt)) {
//			ps.setDouble(1, pct);
//			ps.setString(2, college);
//			ps.execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public String getPing(Response res) {
		//res.type("application/json");
		res.status(200);
		//res.body("pong\n");
		return "pong\n";
	}
	public String resetTable(Request req, Response res) {

		
		res.type("application/json");
		
		String query = "DELETE FROM movies";  

		 try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.executeUpdate();

		} catch (SQLException e) {
		    e.printStackTrace();
		return "caught";
		}
	

		query = "DELETE FROM customers";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.executeUpdate();

		} catch (SQLException e) {
		    e.printStackTrace();
		return "caught";
		}
	
	
		query = "DELETE FROM theaters";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.executeUpdate();

		} catch (SQLException e) {
		    e.printStackTrace();
		return "caught";
		}

		query = "DELETE FROM tickets";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.executeUpdate();
		} catch (SQLException e) {
		    e.printStackTrace();
		return "caught";
		}

		query = "DELETE FROM screenings";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.executeUpdate();
		} catch (SQLException e) {
		    e.printStackTrace();
		return "caught";
		}
	

		String queryCustomers =
		"INSERT INTO customers (user_name, full_name, pass_word)\n" +
		"VALUES  (?, ?, ?)\n";
		String queryMovies =
		"INSERT INTO movies(imdb_key, movie_name, production_year, playtime)\n" +
		"VALUES (?,?,?,?)";
		String queryTheaters = 
		"INSERT INTO theaters(theater_name, capacity)\n" +
		"VALUES (?,?)";

		try{
		PreparedStatement cust = conn.prepareStatement(queryCustomers);
		PreparedStatement cust1 = conn.prepareStatement(queryCustomers);
		PreparedStatement movie = conn.prepareStatement(queryMovies);
		PreparedStatement movie1 = conn.prepareStatement(queryMovies);
		PreparedStatement movie2 = conn.prepareStatement(queryMovies);
		PreparedStatement movie3 = conn.prepareStatement(queryMovies);
		PreparedStatement theater = conn.prepareStatement(queryTheaters);
		PreparedStatement theater1 = conn.prepareStatement(queryTheaters);
		PreparedStatement theater2 = conn.prepareStatement(queryTheaters);
		cust.setString(1, "alice");
		cust.setString(2, "Alice");
		cust.setString(3, passGen.hash("dobido"));
		cust.executeUpdate();
		cust1.setString(1, "bob");
		cust1.setString(2, "Bob");
		cust1.setString(3, passGen.hash("whatsinaname"));
		cust1.executeUpdate();
		movie.setString(1, "tt5580390");
		movie.setString(2, "The Shape of Water");
		movie.setInt(3, 2017);
		movie.setInt(4, 135);
		movie.executeUpdate();
		movie1.setString(1, "tt4975722");
		movie1.setString(2, "Moonlight");
		movie1.setInt(3, 2016);
		movie1.setInt(4, 111);
		movie1.executeUpdate();
		movie2.setString(1, "tt1895587");
		movie2.setString(2, "Spotlight");
		movie2.setInt(3, 2015);
		movie2.setInt(4, 135);
		movie2.executeUpdate();
		movie3.setString(1, "tt2562232");
		movie3.setString(2, "Birdman");
		movie3.setInt(3, 2014);
		movie3.setInt(4, 132);
		movie3.executeUpdate();
		theater.setString(1, "Kino");
		theater.setInt(2, 10);
		theater.executeUpdate();
		theater1.setString(1, "Södran");
		theater1.setInt(2, 16);
		theater1.executeUpdate();
		theater2.setString(1, "Skandia");
		theater2.setInt(2, 100);
		theater2.executeUpdate();
	
		} catch (SQLException e) {
		    e.printStackTrace();
		return "caught";
		}
		res.body("ok");
		res.status(200);
		return "OK";
	}

public String getMovie (Request req, Response res, String imdb_key) {
	res.type("aaplication/json");
	String query = 
		"SELECT imdb_key AS imdbKey, movie_name AS name, production_year AS year\n" + 
		"FROM movies\n" +
		"WHERE imdbKey = ?\n";

	try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, imdb_key);
    	ResultSet rs = ps.executeQuery();
        String result = JSONizer.toJSON(rs, "data");
        res.status(200);
        res.body(result);
        return result;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
	return "";
}

public String getCustomer (Request req, Response res, String username) {
	res.type("application/json");
	String query = 
		"SELECT screening_date AS date, screening_time AS time," + 
			"theater_name AS theater, movie_name AS title," + 
			"production_year AS year, count() AS nbrOfTickets\n" +
		"FROM tickets\n" +
		"JOIN screenings\n" +
		"USING (screening_date, screening_time, theater_name)\n" +
		"WHERE user_name = ?\n" + 
		"GROUP BY screening_id\n";

	try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            String result = JSONizer.toJSON(rs, "data");
            res.status(200);
            res.body(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
}
public String addTicket (Request req, Response res){
	int remaining_seats = 0;
	String screening_time = "";
	String screening_date = "";
	String theater_name  = "";
	String queryFindRemaningSeats =
	"SELECT remaining_seats, screening_time, screening_date, theater_name\n" +
	"FROM screenings\n" +
	"WHERE screening_id = ?";
	try(PreparedStatement ps = conn.prepareStatement(queryFindRemaningSeats)){
		ps.setString(1, req.queryParams("performance"));
		ResultSet rs = ps.executeQuery();
		remaining_seats = rs.getInt(1);
		screening_time = rs.getString(2);
		screening_date = rs.getString(3);
		theater_name = rs.getString(4);
	}catch(SQLException e){
		e.printStackTrace();
		return "Error";
	}
	if(remaining_seats <= 0)
	{
		return "No tickets left";
	}
	String queryPassword =
	"SELECT user_name, pass_word\n" +
	"FROM customers\n" +
	"WHERE user_name = ? AND pass_word = ?";
	try(PreparedStatement ps = conn.prepareStatement(queryPassword)){
		ps.setString(1, req.queryParams("user"));
		ps.setString(2, passGen.hash(req.queryParams("pwd")));
		ResultSet rs = ps.executeQuery();	
	}catch(SQLException e){
		e.printStackTrace();
		return "Wrong password";
	}
	String queryTicket =
	"INSERT INTO tickets (user_name, screening_time, screening_date, theater_name)\n" +
		"VALUES  (?, ?, ?, ?)\n";

	try (PreparedStatement ps = conn.prepareStatement(queryTicket)) {
            ps.setString(1, req.queryParams("user"));
			ps.setString(2, screening_time);
			ps.setString(3, screening_date);
			ps.setString(4, theater_name);
			ps.executeUpdate();
	}
	catch(SQLException e){
		e.printStackTrace();
		return "Error";
	}


	String queryUpdate =
	"UPDATE screenings \n" + 
	"SET remaining_seats = ?\n" + 
	"WHERE screening_id = ?";

	 try (PreparedStatement ps = conn.prepareStatement(queryUpdate)) {
		 	remaining_seats --;
            ps.setInt(1, remaining_seats);
			ps.setString(2, req.queryParams("performance"));
			ps.executeUpdate();
	}
	catch(SQLException e){
		e.printStackTrace();
		return "ErrorInsert";
	}
	return "done" + remaining_seats;
}
public String addPerformance (Request req, Response res) {
	String performanceID = "error";
	String title = "test";
	int capacity_new = 0;
	int year = 666;
	res.type("application/json");
	String queryPerformance = 
		"INSERT INTO screenings(screening_time, screening_date, production_year, movie_name, theater_name, remaining_seats)\n" +
		"VALUES (?,?,?,?,?,?)";
	String queryFind = 
		"SELECT production_year, movie_name \n" +
		"FROM movies\n" +
		"WHERE imdb_key = ?";
	String queryFindTheater =
		"SELECT capacity\n" +
		"FROM theaters\n" +
		"WHERE theater_name = ?";

	try (PreparedStatement ps = conn.prepareStatement(queryFind)) {
		ps.setString(1, req.queryParams("imdb"));
		ResultSet rs = ps.executeQuery();
		year = rs.getInt(1);
		title = rs.getString(2);
	}
	catch(SQLException e){
		e.printStackTrace();
	return "caught";
	}
	try (PreparedStatement ps = conn.prepareStatement(queryFindTheater)) {
		ps.setString(1, req.queryParams("theater"));
		ResultSet rs = ps.executeQuery();
		capacity_new = rs.getInt(1);
	}
	catch(SQLException e){
		e.printStackTrace();
	return "caught";
	}
	// REMAININGSEATS NOT 100 NOT FIXED SPECIAL CASES
	try (PreparedStatement ps = conn.prepareStatement(queryPerformance)) {
		ps.setString(1, req.queryParams("time"));
		ps.setString(2, req.queryParams("date"));
		ps.setInt(3, year);
		ps.setString(4, title);
		ps.setString(5, req.queryParams("theater"));
		ps.setInt(6, capacity_new);
		ps.executeUpdate();

		
	}	
	catch(SQLException e){
		e.printStackTrace();
	return "caught2";
	}
	String queryFindId = 
		"SELECT screening_id \n" +
		"FROM screenings\n" +
		"WHERE theater_name = ? AND screening_date = ? AND screening_time = ?";
		try (PreparedStatement ps2 = conn.prepareStatement(queryFindId)) {
			ps2.setString(1, req.queryParams("theater"));
			ps2.setString(2, req.queryParams("date"));
			ps2.setString(3, req.queryParams("time"));
			ResultSet rs = ps2.executeQuery();
			performanceID = rs.getString(1);
		}
		catch(SQLException e){
		e.printStackTrace();
	return "caught3";
	}
		return "/performances/" + performanceID;
}

public String getPerformances (Request req, Response res) {

res.type("application/json");
String query = 
	"SELECT *\n" +
	"FROM screenings\n";

try (PreparedStatement ps = conn.prepareStatement(query)) {
	ResultSet rs = ps.executeQuery();
        String result = JSONizer.toJSON(rs, "data");
        res.status(200);
        res.body(result);
        return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
	return "OK \n";
}
public String getMovies(Request req, Response res){
	if(true){	
			String title = req.queryParams("title");
			int year = Integer.parseInt(req.queryParams("year"));
			String query = "SELECT imdb_key AS imdbKey, movie_name AS title, production_year AS year \n" +			"FROM movies \n" +
			"WHERE movie_name = ? AND production_year = ? \n";
			
		try (PreparedStatement ps = conn.prepareStatement(query)) {

		ps.setString(1, title);
		ps.setInt(2, year);
		ResultSet rs = ps.executeQuery();
		String result = JSONizer.toJSON(rs, "data");
		res.status(200);
		res.body(result);
		return result;
		}
	catch (SQLException e) {
	e.printStackTrace();
	return query;

	}
	}
else{
	res.type("application/json");
	String query = "SELECT imdb_key AS imdbKey, movie_name AS title, production_year AS year \n" +
	"FROM movies \n";
	try{
	PreparedStatement ps = conn.prepareStatement(query);
	ResultSet rs = ps.executeQuery();
	String result = JSONizer.toJSON(rs, "data");
	res.status(200);
	res.body(result);
	return result;
	
	}catch(SQLException e){
	e.printStackTrace();
	}
	return "";
}}


public String getOneMovie(Request req, Response res, String movieString) {
	if(true) { return "hejsan"; }
	String query = "";
	res.type("application/json");
	if(movieString.indexOf('&') >= 0) {
		String[] parts = movieString.split("&");
		String title = parts[0];
		String year = parts[1];
		String[] partsYear = year.split("=");
		int yearInt = Integer.parseInt(partsYear[1]);
		query = "SELECT imdb_key AS imdbKey, movie_name AS title, production_year AS year \n" +			"FROM movies \n" +
			"WHERE movie_name = ? AND production_year = ? \n";
	try {
	PreparedStatement ps = conn.prepareStatement(query);
	ps.setString(1, title);
	ps.setInt(2, yearInt);
	ResultSet rs = ps.executeQuery();
	String result = JSONizer.toJSON(rs, "data");
	res.status(200);
	res.body(result);
	return "HEJSAN";
//	return result;
	}
	catch (SQLException e) {
	e.printStackTrace();
	return "caught";
	}
	} else {
	
	}
	
	
	return "";
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



/**
 * Auxiliary class for automatically translating a ResultSet to JSON
 */
class PasswordHashGenerator {

    public static String hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return String.format("%064x", new BigInteger(1, digest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

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
