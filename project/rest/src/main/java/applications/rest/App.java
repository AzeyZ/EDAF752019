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
		Database db = new Database("bakery.db");
		port(8888); // Port 7007 in project 3
		
		// The following calls are implemented, but not necessarely tested
		get("/ping", (req, res) -> db.getPing(res)); // Used in lab3
		post("/reset", (req, res) -> db.resetTable(req, res));
		get("/customers", (req, res)-> db.getCustomer(req, res));
		get("/ingredients", (req, res) -> db.getIngredients(req, res));
		get("/cookies", (req, res) -> db.getCookies(req, res));
		get("/recipes", (req, res) -> db.getRecipes(req, res));
		get("/pallets", (req, res) -> db.getPallets(req, res));
		
		// post("/performances", (req, res) -> db.addPerformance(req, res));
		// post("/tickets", (req, res) -> db.addTicket(req, res));
		
		// get("/movies/:imdb-key", (req, res) -> db.getMovie(req, res,
		// req.params(":imdb-key")));
		// get("/customers/:username/tickets", (req, res) -> db.getCustomer(req, res,
		// req.params(":username")));
	}
}

class Database {
	/**
	 * The database connection.
	 */
	private Connection conn;
	private PasswordHashGenerator passGen;

	/**
	 * Creates the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database(String filename) {
		openConnection(filename);
	}

	/**
	 * Opens a connection to the database, using the specified filename (if we'd
	 * used a traditional DBMS, such as PostgreSQL or MariaDB, we would have
	 * specified username and password instead).
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

	// From lab 3, might as well keep it?
	public String getPing(Response res) {
		res.status(200);
		return "pong\n";
	}

	public String resetTable(Request req, Response res) {

		res.type("application/json");

		// Emptying all the tables
		emptyDatabase();
		
		addCustomers();
		addProducts();
		addMaterials();
		addUsedMaterials();
		
		// Should return json object with "status" set to "ok"
		res.body("ok");
		res.status(200);
		return res.toString();
	}

	// Adds all the spciified customers in reset
	private void addCustomers() {
		newCustomer("Finkakor AB", "Helsingborg");
		newCustomer("Småbröd AB", "Malmö");
		newCustomer("Kaffebröd AB", "Landskrona");
		newCustomer("Bjudkakor AB", "Ystad");
		newCustomer("Kalaskakor AB", "Trelleborg");
		newCustomer("Partykakor AB", "Kristianstad");
		newCustomer("Gästkakor AB", "Hässleholm");
		newCustomer("Skånekakor AB", "Perstorp");
	}
	
	// Adds all the spciified products in reset
	private void addProducts() {
		newProduct("Nut ring");
		newProduct("Nut cookie");
		newProduct("Amneris");
		newProduct("Tango");
		newProduct("Almond delight");
		newProduct("Berliner");
	}
	
	// Adds all the spciified materials in reset
	private void addMaterials () {
		newMaterial("Flour", 100000, "g");
		newMaterial("Butter", 100000, "g");
		newMaterial("Icing sugar", 100000, "g");
		newMaterial("Roasted, chopped nuts", 100000, "g");
		newMaterial("Fine-ground nuts", 100000, "g");
		newMaterial("Ground, roasted nuts", 100000, "g");
		newMaterial("Bread crumbs", 100000, "g");
		newMaterial("Sugar", 100000, "g");
		newMaterial("Egg whites", 100000, "ml");
		newMaterial("Chocolate", 100000, "g");
		newMaterial("Marzipan", 100000, "g");
		newMaterial("Eggs", 100000, "g");
		newMaterial("Potato starch", 100000, "g");
		newMaterial("Wheat flour", 100000, "g");
		newMaterial("Sodium bicarbonate", 100000, "g");
		newMaterial("Vanilla", 100000, "g");
		newMaterial("Chopped almonds", 100000, "g");
		newMaterial("Cinnamon", 100000, "g");
		newMaterial("Vanilla sugar", 100000, "g");
	}
	
	// Adds all the spciified recipes in reset
	private void addUsedMaterials () {
		newUsedMaterial("Nut ring", "Flour", 450);
		newUsedMaterial("Nut ring", "Butter", 450);
		newUsedMaterial("Nut ring", "Icing sugar", 190);
		newUsedMaterial("Nut ring", "Roasted, chopped nuts", 225);
		
		newUsedMaterial("Nut cookie", "Fine-ground nuts", 750);
		newUsedMaterial("Nut cookie", "Ground, roasted nuts", 625);
		newUsedMaterial("Nut cookie", "Bread crumbs", 125);
		newUsedMaterial("Nut cookie", "Sugar", 375);
		newUsedMaterial("Nut cookie", "Egg whites", 350);
		newUsedMaterial("Nut cookie", "Chocolate", 50);
		
		newUsedMaterial("Amneris", "Marzipan", 750);
		newUsedMaterial("Amneris", "Butter", 250);
		newUsedMaterial("Amneris", "Eggs", 250);
		newUsedMaterial("Amneris", "Potato starch", 25);
		newUsedMaterial("Amneris", "Wheat flour", 25);
		
		newUsedMaterial("Tango", "Butter", 200);
		newUsedMaterial("Tango", "Sugar", 250);
		newUsedMaterial("Tango", "Flour", 300);
		newUsedMaterial("Tango", "Sodium bicarbonate", 4);
		newUsedMaterial("Tango", "Vanilla", 2);
		
		newUsedMaterial("Almond delight", "Butter", 400);
		newUsedMaterial("Almond delight", "Sugar", 270);
		newUsedMaterial("Almond delight", "Chopped almonds", 279);
		newUsedMaterial("Almond delight", "Flour", 400);
		newUsedMaterial("Almond delight", "Cinnamon", 10);
		
		newUsedMaterial("Berliner", "Flour", 350);
		newUsedMaterial("Berliner", "Butter", 250);
		newUsedMaterial("Berliner", "Icing sugar", 100);
		newUsedMaterial("Berliner", "Eggs", 50);
		newUsedMaterial("Berliner", "Vanilla sugar", 5);
		newUsedMaterial("Berliner", "Chocolate", 50);
	}
	
	private void emptyDatabase() {
		ArrayList<String> names = new ArrayList<>();
		names.add("products");
		names.add("materials");
		names.add("used_materials");
		names.add("restocks");
		names.add("pallets");
		names.add("deliveries");
		names.add("customers");
		names.add("orders");
		
		for (String name : names) {
			String query = "DELETE FROM " + name;

			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean newCustomer(String name, String address) {
		String queryCustomers = "INSERT INTO customers (customer_name, address)\n" + 
			"VALUES  (?, ?)\n";

		try {
			PreparedStatement cust = conn.prepareStatement(queryCustomers);
			cust.setString(1, name);
			cust.setString(2, address);
			cust.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean newProduct(String product_name) {
		String queryProducts = "INSERT INTO products(product_name)\n" + 
			"VALUES (?)";
		try {
			PreparedStatement product = conn.prepareStatement(queryProducts);
			product.setString(1, product_name);
			product.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean newMaterial(String ingredient, int amount, String unit) {
		String queryMaterials = "INSERT INTO materials(ingredient, amount)\n" + 
			"VALUES (?, ?, ?)";
		try {
			PreparedStatement material = conn.prepareStatement(queryMaterials);
			material.setString(1, ingredient);
			material.setInt(2, amount);
			material.setString(3, unit);
			material.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean newUsedMaterial(String product_name, String ingredient, int used_amount) {
		String queryUsedMaterials = "INSERT INTO used_materials(used_amount, unit, product_name)\n" + 
			"VALUES (?, ?, ?)";
		try {
			PreparedStatement usedMaterial = conn.prepareStatement(queryUsedMaterials);
			usedMaterial.setString(1, product_name);
			usedMaterial.setString(2, ingredient);
			usedMaterial.setInt(3, used_amount);
			usedMaterial.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getCustomer(Request req, Response res) {
		res.type("application/json");
		String query = "SELECT customer_name, address\n"
			+ "FROM customers";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "customers");
			res.status(200);
			res.body(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getIngredients(Request req, Response res) {
		res.type("application/json");
		String query = "SELECT ingredient, amount, unit\n"
			+ "FROM materials";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "ingredients");
			res.status(200);
			res.body(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public String getCookies (Request req, Response res) {
		res.type("application/json");
		String query = "SELECT product_name AS name\n"
			+ "FROM products";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "cookies");
			res.status(200);
			res.body(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public String getRecipes (Request req, Response res) {
		res.type("application/json");
		String query = "SELECT product_name AS cookie, ingredient, amount AS quantity, unit\n"
			+ "FROM used_material\n"
			+ "JOIN materials\n"
			+ "USING ingredient\n"
			+ "ORDER BY product_name, ingredient";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "recipes");
			res.status(200);
			res.body(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public String addPallet (Request req, Response res, String cookieName) {
		// Each pallet contains 15*10*36=5400 cookies
		// Recipes are described for 100 cookies
		return null;
	}
	
	public String getPallets(Request req, Response res) {
		res.type("application/json");
		String query = "SELECT pallet_id AS id, product_name AS cookie, production_date AS productionDate, customer_name AS customer, blocked\n"
			+ "FROM pallets\n";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "pallets");
			res.status(200);
			res.body(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String addTicket(Request req, Response res) {
		int remaining_seats = 0;
		String screening_time = "";
		String screening_date = "";
		String theater_name = "";
		String ticketID = "tes";
		String queryFindRemaningSeats = "SELECT remaining_seats, screening_time, screening_date, theater_name\n"
				+ "FROM screenings\n" + "WHERE screening_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(queryFindRemaningSeats)) {
			ps.setString(1, req.queryParams("performance"));
			ResultSet rs = ps.executeQuery();
			remaining_seats = rs.getInt(1);
			screening_time = rs.getString(2);
			screening_date = rs.getString(3);
			theater_name = rs.getString(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error";
		}
		if (remaining_seats <= 0) {
			return "No tickets left";
		}
		String queryPassword = "SELECT pass_word\n" + "FROM customers\n" + "WHERE user_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(queryPassword);
			ps.setString(1, req.queryParams("user"));
			ResultSet rs = ps.executeQuery();
			if (!rs.getString(1).equals(passGen.hash(req.queryParams("pwd")))) {
				return "Wrong password";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error";
		}
		String queryTicket = "INSERT INTO tickets (user_name, screening_time, screening_date, theater_name)\n"
				+ "VALUES  (?, ?, ?, ?)\n";

		try (PreparedStatement ps = conn.prepareStatement(queryTicket)) {
			ps.setString(1, req.queryParams("user"));
			ps.setString(2, screening_time);
			ps.setString(3, screening_date);
			ps.setString(4, theater_name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error";
		}

		String queryUpdate = "UPDATE screenings \n" + "SET remaining_seats = ?\n" + "WHERE screening_id = ?";

		try (PreparedStatement ps = conn.prepareStatement(queryUpdate)) {
			remaining_seats--;
			ps.setInt(1, remaining_seats);
			ps.setString(2, req.queryParams("performance"));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "ErrorInsert";
		}
		String queryFindId = "SELECT ticket_id\n" + "FROM tickets\n" + "WHERE rowid = last_insert_rowid()";
		try (PreparedStatement ps2 = conn.prepareStatement(queryFindId)) {
			ResultSet rs = ps2.executeQuery();
			ticketID = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "caught3";
		}
		return "/ticket/" + ticketID;

	}

	public String addPerformance(Request req, Response res) {
		String performanceID = "error";
		String title = "test";
		int capacity_new = 0;
		int year = 666;
		res.type("application/json");
		String queryPerformance = "INSERT INTO screenings (screening_time, screening_date, production_year, movie_name, theater_name, remaining_seats)\n"
				+ "VALUES (?,?,?,?,?,?)";
		String queryFind = "SELECT production_year, movie_name \n" + "FROM movies\n" + "WHERE imdb_key = ?";
		String queryFindTheater = "SELECT capacity\n" + "FROM theaters\n" + "WHERE theater_name = ?";

		try (PreparedStatement ps = conn.prepareStatement(queryFind)) {
			ps.setString(1, req.queryParams("imdb"));
			ResultSet rs = ps.executeQuery();
			year = rs.getInt(1);
			title = rs.getString(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return "No such movie or theater";
		}
		try (PreparedStatement ps = conn.prepareStatement(queryFindTheater)) {
			ps.setString(1, req.queryParams("theater"));
			ResultSet rs = ps.executeQuery();
			capacity_new = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "No such movie or theater";
		}

		try (PreparedStatement ps = conn.prepareStatement(queryPerformance)) {
			ps.setString(1, req.queryParams("time"));
			ps.setString(2, req.queryParams("date"));
			ps.setInt(3, year);
			ps.setString(4, title);
			ps.setString(5, req.queryParams("theater"));
			ps.setInt(6, capacity_new);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "#369_multiple_error?";
		}
		String queryFindId = "SELECT screening_id \n" + "FROM screenings\n"
				+ "WHERE theater_name = ? AND screening_date = ? AND screening_time = ?";
		try (PreparedStatement ps2 = conn.prepareStatement(queryFindId)) {
			ps2.setString(1, req.queryParams("theater"));
			ps2.setString(2, req.queryParams("date"));
			ps2.setString(3, req.queryParams("time"));
			ResultSet rs = ps2.executeQuery();
			performanceID = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "caught3";
		}
		return "/performances/" + performanceID;
	}

	public String getPerformances(Request req, Response res) {

		res.type("application/json");
		String query = "SELECT *\n" + "FROM screenings\n";

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

	public String getMovies(Request req, Response res) {
		if (req.queryParams().size() == 2) {
			String title = req.queryParams("title");
			int year = Integer.parseInt(req.queryParams("year"));
			String query = "SELECT imdb_key AS imdbKey, movie_name AS title, production_year AS year \n"
					+ "FROM movies \n" + "WHERE movie_name = ? AND production_year = ? \n";

			try (PreparedStatement ps = conn.prepareStatement(query)) {

				ps.setString(1, title);
				ps.setInt(2, year);
				ResultSet rs = ps.executeQuery();
				String result = JSONizer.toJSON(rs, "data");
				res.status(200);
				res.body(result);
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
				return query;

			}
		} else {
			res.type("application/json");
			String query = "SELECT imdb_key AS imdbKey, movie_name AS title, production_year AS year \n"
					+ "FROM movies \n";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
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

