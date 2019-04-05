/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package applications.rest;

import java.time.LocalDate;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import spark.*;
import static spark.Spark.*;
import com.google.gson.Gson;
//import com.sun.tools.internal.ws.processor.model.Request;
//import com.sun.tools.internal.ws.processor.model.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class App {

	public static void main(String[] args) {
		Database db = new Database("bakery.db");
		port(8888); // Port 7007 in project 3
		
		get("/ping", (req, res) -> db.getPing(res)); // Used in lab3
		post("/reset", (req, res) -> db.resetTable(req, res));
		get("/customers", (req, res)-> db.getCustomer(req, res));
		get("/ingredients", (req, res) -> db.getIngredients(req, res));
		get("/cookies", (req, res) -> db.getCookies(req, res));
		get("/recipes", (req, res) -> db.getRecipes(req, res));
		get("/pallets", (req, res) -> db.getPallets(req, res));
		post("/pallets", (req, res) -> db.addPallet(req, res));
		post("/block/*/*/*", (req, res) -> db.blockPallets(req, res));
		post("/unblock/*/*/*", (req, res) -> db.unBlockPallets(req, res));

		
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
	// private PasswordHashGenerator passGen;

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
		res.status(200);
		return "ok";
	}
	public String blockPallet(Request req, Response res){
		return null;
	}
	public String unblockPallet(Request req, Response res){
		return null;
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
		
		newUsedMaterial(450, "Flour", "Nut ring", "g");
		newUsedMaterial(450, "Butter", "Nut ring", "g");
		newUsedMaterial(190, "Icing sugar", "Nut ring", "g");
		newUsedMaterial(225, "Roasted, chopped nuts", "Nut ring", "g");
		
		newUsedMaterial(750, "Fine-ground nuts", "Nut cookie", "g");
		newUsedMaterial(625, "Ground, roasted nuts", "Nut cookie", "g");
		newUsedMaterial(125, "Bread crumbs", "Nut cookie", "g");
		newUsedMaterial(375, "Sugar", "Nut cookie", "g");
		newUsedMaterial(350, "Egg whites", "Nut cookie", "ml");
		newUsedMaterial(50, "Chocolate", "Nut cookie", "g");
		
		newUsedMaterial(750, "Marzipan", "Amneris", "g");
		newUsedMaterial(250, "Butter", "Amneris", "g");
		newUsedMaterial(250, "Eggs", "Amneris", "g");
		newUsedMaterial(25, "Patato starch", "Amneris", "g");
		newUsedMaterial(25, "Wheat flour", "Amneris", "g");
		
		newUsedMaterial(200, "Butter", "Tango", "g");
		newUsedMaterial(250, "Sugar", "Tango", "g");
		newUsedMaterial(300, "Flour", "Tango", "g");
		newUsedMaterial(4, "Sodium bicarbonate", "Tango", "g");
		newUsedMaterial(2, "Vanilla", "Tango", "g");
		
		newUsedMaterial(400, "Butter", "Almond delight", "g");
		newUsedMaterial(270, "Sugar", "Almond delight", "g");
		newUsedMaterial(279, "Chopped almonds", "Almond delight", "g");
		newUsedMaterial(400, "Flour", "Almond delight", "g");
		newUsedMaterial(10, "Cinnamon", "Almond delight", "g");
		
		newUsedMaterial(350, "Flour", "Berliner", "g");
		newUsedMaterial(250, "Butter", "Berliner", "g");
		newUsedMaterial(100, "Icing sugar", "Berliner", "g");
		newUsedMaterial(50, "Eggs", "Berliner", "g");
		newUsedMaterial(5, "Vanilla sugar", "Berliner", "g");
		newUsedMaterial(50, "Chocolate", "Berliner", "g");
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
		String queryMaterials = "INSERT INTO materials(ingredient, amount, unit)\n" + 
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

	private boolean newUsedMaterial(int used_amount, String ingredient, String product_name, String unit) {
		String queryUsedMaterials = "INSERT INTO used_materials(used_amount, ingredient, product_name, unit)\n" + 
			"VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement usedMaterial = conn.prepareStatement(queryUsedMaterials);
			usedMaterial.setInt(1, used_amount);
			usedMaterial.setString(2, ingredient);
			usedMaterial.setString(3, product_name);
			usedMaterial.setString(4, unit);
			usedMaterial.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getCustomer(Request req, Response res) {
		res.type("application/json");
		String query = "SELECT *\n"
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
		return "error";
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
		String query = 
			"SELECT product_name AS cookie, ingredient, used_amount AS quantity, unit\n" +
			"FROM used_materials\n" +
			"ORDER BY product_name, ingredient";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "recipes");
			res.status(200);
			res.body(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "error";
	}
	
	public String addPallet (Request req, Response res) {
		// Compare with "getMovies" in lab 3
		// Each pallet contains 15*10*36=5400 cookies
		// Recipes are described for 100 cookies
		// 54 recipes / pallet

		res.type("application/json");
		
		String cookie_name = req.queryParams("cookie");
		
		if (findCookie(req, res, cookie_name).equals("cookie not found"))
			return findCookie(req, res, cookie_name);
		
		if (compareIngredients(req, res, cookie_name).equals("error") || compareIngredients(req, res, cookie_name).equals("Not enough ingredients!"))
			return compareIngredients(req, res, cookie_name);
		
		// If no returns above we insert a pallet
		if (insertPallet(req, res, cookie_name).equals("error"))
			return insertPallet(req, res, cookie_name);
		
		// Find ingredients and how much are used for specified cookie
		// Update values in materials
		if (updateMaterials(req, res, cookie_name).equals("Update values failed!") || updateMaterials(req, res, cookie_name).equals("getValues failed"))
			return updateMaterials(req, res, cookie_name);
			
		return "Cookie added!";
	}
	
	private String findCookie(Request req, Response res, String cookie_name) {
		String queryFindCookie = "SELECT * FROM products WHERE product_name = ?";
		try (PreparedStatement ps = conn.prepareStatement(queryFindCookie)) {
			ps.setString(1,  cookie_name);
			ResultSet rs = ps.executeQuery();

			if(rs.isClosed()) {
				return "cookie not found";
			}
			
			return "cookie found";
		} catch (SQLException e) {
			e.printStackTrace();
			return "cookie not found";
		}
	}
	
	private String compareIngredients (Request req, Response res, String cookie_name) {
		int amount;
		int used_amount;
		String queryCompareIngredient = 
		    "SELECT amount, used_amount, ingredient\n" + 
		    "FROM used_materials\n" +
		    "JOIN materials\n" + 
		    "USING (ingredient)\n" +
		    "WHERE product_name = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(queryCompareIngredient)) {
			ps.setString(1, cookie_name);
			ResultSet rs = ps.executeQuery();
			
			while (true) {
				amount = rs.getInt(1);
				used_amount = rs.getInt(2);
				if ((used_amount * 54) > amount) {
					return "Not enough ingredients!";
				}
				
				if (!rs.next()) {
					break;
				}
			}
			
			return "Enough ingredients!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	private String insertPallet (Request req, Response res, String cookie_name) {
		LocalDate date = LocalDate.now();
		
		String queryInsert = 
			"INSERT INTO pallets (production_date, blocked, product_name)\n" +
			"VALUES (?, ?, ?)\n";
		
		try (PreparedStatement ps = conn.prepareStatement(queryInsert)) {
			ps.setString(1, date.toString());
			ps.setBoolean(2, false);
			ps.setString(3, cookie_name);
			ps.executeUpdate();
			return "added " + cookie_name; //TODO fix print
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	private String updateMaterials (Request req, Response res, String cookie_name) {
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<Integer> usedAmounts = new ArrayList<Integer>();
		
		String queryGetUsedValues = 
			"SELECT used_amount, ingredient, product_name\n" +
		    "FROM used_materials\n" +
			"WHERE product_name = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(queryGetUsedValues)) {
			ps.setString(1, cookie_name);
			ResultSet rs = ps.executeQuery();
			
			while (true) {		
				ingredients.add(rs.getString(2));
				usedAmounts.add(new Integer(rs.getInt(1)));
				
				if (!rs.next()) {
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "getValues failed";
		}
		
		System.out.println(usedAmounts.size());
		
		String queryUpdateMaterials = 
			"UPDATE materials\n" + 
		    "SET amount = amount - (? * 54)\n" +
			"WHERE ingredient = ?";
		try (PreparedStatement ps = conn.prepareStatement(queryUpdateMaterials)) {
			int index = 0;
			while (index < usedAmounts.size()) {
				ps.setInt(1, usedAmounts.get(index));
				ps.setString(2, ingredients.get(index));
				ps.executeUpdate();
				index++;
			}
			return "Cookie added!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Update values failed!";
		}
	}
	
	public String getPallets(Request req, Response res) {
		res.type("application/json");
		
		if(req.queryParams().size() == 0) {
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
		else {
			String cookie_name = "";
			String blocked = "";
			String after = "";
			String before = ""; 
			
			boolean whereAdded = false;
			boolean cookieFirst = false;
			boolean blockedFirst = false;
			boolean afterFirst = false;
			boolean blockedSecond = false;
			boolean afterSecond = false;
			boolean afterThird = false;
			
			String query = "SELECT pallet_id AS id, product_name AS cookie, production_date AS productionDate, customer_name AS customer, blocked\n"
					+ "FROM pallets\n";

			
			if(req.queryParams("cookie") != null) {
				cookie_name = req.queryParams("cookie");
				query = query + " WHERE product_name = ? ";
				whereAdded = true;
				cookieFirst = true;
			}
			
			if(req.queryParams("blocked") != null) {
				if(req.queryParams("blocked").equals("1")) {
					blocked = "1";
				}
				else
				{
					blocked = "0";
				}
				if(!whereAdded) {
					query = query + "WHERE blocked = ? ";
					whereAdded = true;
					blockedFirst = true;
				}
				else {
					query = query + "AND blocked = ? ";
					blockedSecond = true;
				}
				
			}
			
			if(req.queryParams("after") != null) {
				after = req.queryParams("after");
				if(!whereAdded) {
					query = query + "WHERE production_date > ? ";
					whereAdded = true;
					afterFirst = true;
				}
				else {
					query = query + "AND production_date > ? ";
					afterSecond = true;
					if(blockedSecond) {
						afterThird = true;
					}
				}
			}
			if(req.queryParams("before") != null) {
				before = req.queryParams("before");
				if(!whereAdded) {
					query = query + "WHERE production_date < ? ";
				}
				else {
					query = query + "AND production_date < ? ";
					if(blockedSecond) {
						afterThird = false;
					}
				}
			}			
		
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				
				System.out.println(cookieFirst + "cookieFirst");
				System.out.println(blockedFirst + "blockedFirst");
				System.out.println(afterFirst+ "afterFirst");
				System.out.println(blockedSecond+ "blockedSecond");
				System.out.println(afterSecond + "afterSecond");
				System.out.println(afterThird+ "afterThird");
				
			if(req.queryParams().size() == 4) {
				ps.setString(1, cookie_name);
				ps.setString(2, blocked);
				ps.setString(3, after);
				ps.setString(4, before);			
			}
			else if(req.queryParams().size() == 1) {
				if(cookie_name != "") {
					ps.setString(1, cookie_name);
				}
				else if(blocked != "") {
					ps.setString(1, blocked);
				}
				else if(after != "") {
					ps.setString(1, after);
				}
				else if(before != "") {
					ps.setString(1, before);
				}
			}
			else {
					
				if(cookieFirst) {
					ps.setString(1, cookie_name);
				}
				else if(blockedFirst) {
					ps.setString(1, blocked);
				}
				else if(afterFirst) {
					ps.setString(1, after);
				}
				else {
					ps.setString(1, before);
				}
				if(blockedSecond) {
					ps.setString(2, blocked);
				}
				else if(afterSecond) {
					ps.setString(2, after);
				}
				else {
					ps.setString(2, before);
				}
				if(req.queryParams().size() == 3) {	
					if(afterThird) {
						ps.setString(3, after);
					}
					else {
						ps.setString(3, before);
					}
				}
			}			
			ResultSet rs = ps.executeQuery();
			String result = JSONizer.toJSON(rs, "pallets");
			res.status(200);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return "";
	}
	
	public String blockPallets(Request req, Response res) {
		res.type("application/json");
		String cookie_name = req.splat()[0];
		String from_date = req.splat()[1];
		String to_date = req.splat()[2];
		String queryUpdate = "UPDATE pallets SET blocked = true WHERE product_name = ? AND production_date <= ? AND production_date >= ? ";
			try (PreparedStatement ps = conn.prepareStatement(queryUpdate)) {
				ps.setString(1, cookie_name);
				ps.setString(2, to_date);
				ps.setString(3, from_date);
				ps.executeUpdate();
				return "ok";
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "error";
		}
	
	public String unBlockPallets(Request req, Response res) {
		res.type("application/json");
		String cookie_name = req.splat()[0];
		String from_date = req.splat()[1];
		String to_date = req.splat()[2];
		String queryUpdate = "UPDATE pallets SET blocked = false WHERE product_name = ? AND production_date <= ? AND production_date >= ? ";
			try (PreparedStatement ps = conn.prepareStatement(queryUpdate)) {
				ps.setString(1, cookie_name);
				ps.setString(2, to_date);
				ps.setString(3, from_date);
				ps.executeUpdate();
				return "ok";
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "error";
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

