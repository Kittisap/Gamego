package com.gamego.db;

import java.util.*;
import java.util.Date;
import java.sql.*;
import com.gamego.user.*;
import com.gamego.game.*;
import com.gamego.cart.*;
import com.gamego.search.*;

public class Database
{
	private DBConnectionPool pool = null;
	private Connection conn = null;
	
	public static final String DB_HOST = "jdbc:mysql://localhost:3306/gamego";
	public static final String DB_USER = "root";
	public static final String DB_PASS = "admin";
	
	public Database()
	{
		try
		{
			pool = new DBConnectionPool(DB_HOST, DB_USER, DB_PASS);
			conn = (pool != null ? pool.getConnection() : null);
		}
		catch(Exception e) {}
	}
	
	public void close()
	{
		try
		{
			if(pool != null && conn != null)
				pool.returnConnection(conn);
		}
		catch(Exception e) {}
	}
	
	public void registerUser(User user) throws Exception
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * " + 
				"FROM member " + 
				"WHERE username = '" + user.getUsername() + "' " + 
				"OR email = '" + user.getEmail() + "'";
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			String username = rs.getString("username");
			String email = rs.getString("email");
			
			if(username.equals(user.getUsername()))
			{
				rs.close();
				stmt.close();
				this.close();
				
				throw new Exception("Username is taken.");
			}
			
			if(email.equals(user.getEmail()))
			{
				rs.close();
				stmt.close();
				this.close();
				
				throw new Exception("Email is already being used.");
			}
		}
		
		sql = "INSERT INTO member " + 
				"(customerId, username, password, email) " + 
				"VALUES (NULL, '" + 
				user.getUsername() + "', '" + 
				user.getPassword() + "', '" + 
				user.getEmail() + "')";
		
		if(stmt.executeUpdate(sql) == 0)
			throw new Exception("Unknown error occurred.");
	}
	
	public boolean verifyUser(User user)
	{
		if(conn == null || user == null)
			return false;
		
		Statement stmt = null;
		ResultSet rs = null;
		boolean isVerified = false;
		
		try
		{
			stmt = conn.createStatement();
			
			if(stmt != null)
			{
				String sql = "SELECT * " + 
						"FROM member " + 
						"WHERE username = '" + user.getUsername() + "'";

				rs = stmt.executeQuery(sql);
				
				if(rs != null && rs.next())
				{
					int id = rs.getInt("customerId");
					String username = rs.getString("username");
					String password = rs.getString("password");
					String email = rs.getString("email");
					Date dateRegisterd = rs.getDate("dateRegistered");
					boolean isAdmin = rs.getInt("admin") == 1 ? true : false;
					
					if(user.getPassword().equals(password))
					{	
						user.setID(id);
						user.setUsername(username);
						user.setPassword(password);
						user.setEmail(email);
						user.setDateRegistered(dateRegisterd);
						user.setAdmin(isAdmin);
						
						isVerified = true;
					}
				}
			}
		}
		catch(Exception e) {}
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(stmt != null)
					stmt.close();
			}
			catch(Exception e) {}
			
			close();
		}
		
		return isVerified;
	}
	
	public int addCart(String[] gameIds, int customerId)
	{
		Statement stmt = null;
		ResultSet result = null;
		String query;
		int cartId = 0;
		
		if(gameIds.length == 0)
			return cartId;
		
		try 
		{
			stmt = conn.createStatement();
			query = "INSERT INTO cart ";
			query += "VALUES(null,"+ customerId +",null) ";
			stmt.executeUpdate(query);
			query = "SELECT cartId ";
			query += "FROM cart ";
			query += "WHERE customerId=" + customerId + " ";
			query += "ORDER BY transactionDate desc";
			result = stmt.executeQuery(query);
			if(result.next())
			{
				cartId = result.getInt(1);
			}
			for (int i = 0; i < gameIds.length; i++)
			{
				query = "INSERT INTO cartgame ";
				query += "VALUES("+ cartId +"," + gameIds[i] + ")";
				stmt.executeUpdate(query);
				query = "UPDATE game ";
				query += "SET gameStock = gameStock - 1 ";
				query += "WHERE gameId =" + gameIds[i];
				stmt.executeUpdate(query);
			}
		} 
		catch (SQLException e) {}
		
		return cartId;
	}
	
	public Game selectGame(int gameID)
	{
		if(conn == null)
			return null;
		
		Game game = null;
		Statement stmt = null;
		ResultSet gameRS = null;
		ResultSet gameRatingRS = null;
		ResultSet gameGenreRS = null;
		
		try
		{
			stmt = conn.createStatement();
			
			if(stmt != null)
			{
				String sql = "SELECT game.gameID, game.gameTitle, " + 
						"game.gameDescription, game.gameReleaseDate, " + 
						"game.gameBoxArtPath, game.gameStock, " + 
						"game.gamePrice, developer.*, publisher.*, " + 
						"esrbrating.* " + 
						"FROM game " + 
						"LEFT JOIN developer " + 
						"ON game.gameDeveloper = developer.developerID " + 
						"LEFT JOIN publisher " + 
						"ON game.gamePublisher = publisher.publisherID " + 
						"LEFT JOIN esrbrating " + 
						"ON game.gameESRBRating = esrbrating.esrbRatingID " + 
						"WHERE game.gameID = " + gameID;
				
				gameRS = stmt.executeQuery(sql);
				
				if(gameRS.next())
				{
					// Game Attributes
					String gameTitle = gameRS.getString("gameTitle");
					String gameDescription = gameRS.getString("gameDescription");
					String gameReleaseDate = gameRS.getString("gameReleaseDate");
					String gameBoxArtPath = gameRS.getString("gameBoxArtPath");
					int gameStock = gameRS.getInt("gameStock");
					float gamePrice = gameRS.getFloat("gamePrice");
					
					// Developer Attributes
					int developerID = gameRS.getInt("developerID");
					String developerName = gameRS.getString("developerName");
					
					// Create the developer.
					Developer developer = new Developer(developerID, developerName);
					
					// Publisher Attributes
					int publisherID = gameRS.getInt("publisherID");
					String publisherName = gameRS.getString("publisherName");
					
					// Create the publisher.
					Publisher publisher = new Publisher(publisherID, publisherName);
					
					// ESRB Rating Attributes
					int esrbRatingID = gameRS.getInt("esrbRatingID");
					String esrbRatingName = gameRS.getString("esrbRatingName");
					String esrbRatingAlias = gameRS.getString("esrbRatingAlias");
					
					// Create the ESRB Rating.
					ESRBRating esrbRating = new ESRBRating(esrbRatingID, 
							esrbRatingName, esrbRatingAlias);
					
					// User Rating
					sql = "SELECT AVG(rating.rating) as userRating " + 
							"FROM rating " + 
							"WHERE rating.gameID = " + gameID;
					
					gameRatingRS = stmt.executeQuery(sql);
					
					float userRating = 0f;
					
					if(gameRatingRS != null && gameRatingRS.next())
						userRating = gameRatingRS.getFloat("userRating");
					
					// Genres
					Vector<Genre> genres = new Vector<Genre>();
					int genreID = 0;
					String genreName = "Unknown";

					sql = "SELECT * " + 
							"FROM genre " + 
							"WHERE genre.genreID " + 
							"IN (SELECT gameGenre.genreID " + 
								"FROM gamegenre " + 
								"WHERE gameID = " + gameID + ")";
					
					gameGenreRS = stmt.executeQuery(sql);
					
					while(gameGenreRS.next())
					{
						genreID = gameGenreRS.getInt("genreID");
						genreName = gameGenreRS.getString("genreName");
						
						genres.add(new Genre(genreID, genreName));
					}
					
					// Create the game.
					game = new Game(gameID, gameTitle, gameDescription, 
							developer, publisher, genres, gameReleaseDate, 
							esrbRating, userRating, gameBoxArtPath, 
							gameStock, gamePrice);
				}
			}
		}
		catch(Exception e) {}
		finally
		{
			try
			{
				if(gameRS != null)
					gameRS.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(gameGenreRS != null)
					gameGenreRS.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(gameRatingRS != null)
					gameRatingRS.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(stmt != null)
					stmt.close();
			}
			catch(Exception e) {}
			
			close();
		}
		
		return game;
	}
	
	public SearchResult performSearch(String query)
	{
		return performSearch(query, SearchResult.DEFAULT_GENRE_ID, 
				SearchResult.DEFAULT_PAGE);
	}
	
	public SearchResult performSearch(String query, String genreID, String page)
	{
		int numericGenreID = SearchResult.DEFAULT_GENRE_ID;
		int numericPage = SearchResult.DEFAULT_PAGE;
		
		try
		{
			numericGenreID = Integer.parseInt(genreID);
			numericPage = Integer.parseInt(page);
		}
		catch(Exception e) {}
		
		return performSearch(query, numericGenreID, numericPage);
	}
	
	public SearchResult performSearch(String query, int genreID, int page)
	{
		SearchResult search = null;
		Statement stmt = null;
		ResultSet countRS = null;
		ResultSet searchRS = null;
		
		try
		{
			search = new SearchResult(query, genreID);
			stmt = conn.createStatement();
			
			if(stmt != null && !search.getQuery().isEmpty())
			{	
				String sql = "";
				
				if(search.getGenreID() == SearchResult.DEFAULT_GENRE_ID)
				{
					sql = "SELECT COUNT(*) as numRows " + 
							"FROM game " + 
							"WHERE gameTitle " + 
							"LIKE '%" + search.getQuery() + "%'";
				}
				else
				{
					sql = "SELECT COUNT(*) as numRows " + 
							"FROM game " + 
							"WHERE game.gameID " +
							"IN (SELECT gamegenre.gameID " + 
								"FROM gamegenre " + 
								"WHERE gamegenre.genreID = " + search.getGenreID() + ") " + 
							"AND game.gameTitle " + 
							"LIKE '%" + search.getQuery() + "%'";
				}
				
				countRS = stmt.executeQuery(sql);
				
				if(countRS.next())
					search.setTotalResults(countRS.getInt("numRows"));
				
				search.setCurrentPage(page);
				
				if(search.getGenreID() == SearchResult.DEFAULT_GENRE_ID)
				{
					sql = "SELECT gameID " + 
							"FROM game " + 
							"WHERE gameTitle " + 
							"LIKE '%" + search.getQuery() + "%' " + 
							"ORDER BY gameTitle " + 
							"LIMIT " + search.getOffset() + ", " + search.getLimit();
				}
				else
				{
					sql = "SELECT game.gameID " + 
							"FROM game " + 
							"WHERE game.gameID " + 
							"IN (SELECT gamegenre.gameID " + 
								"FROM gamegenre " + 
								"WHERE gamegenre.genreID = " + search.getGenreID() + ") " + 
							"AND game.gameTitle " + 
							"LIKE '%" + search.getQuery() + "%' " + 
							"ORDER BY game.gameTitle " + 
							"LIMIT " + search.getOffset() + ", " + search.getLimit();
				}
				
				searchRS = stmt.executeQuery(sql);
				
				int gameID = 0;
				Game gameResult = null;
				
				while(searchRS.next())
				{
					gameID = searchRS.getInt("gameID");
					gameResult = selectGame(gameID);
					
					if(gameResult != null)
						search.addResult(gameResult);
				}
			}
		}
		catch(Exception e) {}
		finally
		{
			try
			{
				if(countRS != null)
					countRS.close();
			}
			catch(Exception e) {}

			try
			{
				if(searchRS != null)
					searchRS.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(stmt != null)
					stmt.close();
			}
			catch(Exception e) {}
			
			close();
		}
		
		return search;
	}
	
	public Vector<Genre> getGenres()
	{
		Statement stmt = null;
		ResultSet rs = null;
		Vector<Genre> genres = new Vector<Genre>();
		
		String sql = "SELECT * " + 
				"FROM genre " + 
				"ORDER BY genreName";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			int genreID = 0;
			String genreName = "N/A";
			Genre genre = null;
			
			while(rs.next())
			{
				genreID = rs.getInt("genreID");
				genreName = rs.getString("genreName");
				genre = new Genre(genreID, genreName);
				
				genres.add(genre);
			}
		}
		catch(Exception e) {}
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(stmt != null)
					stmt.close();
			}
			catch(Exception e) {}
			
			close();
		}
		
		return genres;
	}
	
	public Vector<Transaction> getUserHistory(int userID)
	{
		Statement stmt = null;
		ResultSet rs = null;
		Vector<Transaction> history = new Vector<Transaction>();
		
	    String sql = "SELECT * " + 
	    		"FROM game " + 
	    		"INNER JOIN cartgame " + 
	    		"ON cartgame.gameId = game.gameID " + 
	    		"INNER JOIN cart " + 
	    		"ON cart.cartId = cartgame.cartId " + 
	    		"LEFT JOIN rating " + 
	    		"ON game.gameID = rating.gameId " + 
	    		"AND cart.customerId = rating.customerId " + 
	    		"WHERE cart.customerId = " + userID + " " + 
	    		"ORDER BY cart.transactionDate DESC";
	    
	    try
	    {
	    	stmt = conn.createStatement();
	    	rs = stmt.executeQuery(sql);
	    	
	    	if(rs != null)
	    	{
	    		int currentCartID = 0;
	    		Transaction transaction = null;

	    		while(rs.next())
	    		{
	    			int cartID = rs.getInt("cartId");
	    			Date transactionDate = rs.getDate("transactionDate");
	    			
	    			if(cartID != currentCartID)
	    			{
	    				if(currentCartID != 0)
	    					history.add(transaction);

	    				transaction = new Transaction(userID, cartID, transactionDate);
	    				currentCartID = cartID;
	    			}

	    			int gameID = rs.getInt("gameID");
	    			Game game = selectGame(gameID);
	    			int rating = rs.getInt("rating");
	    			TransactionItem item = new TransactionItem(game, rating);
	    			
	    			transaction.addItem(item);
	    		}

	    		if(transaction != null)
	    			history.add(transaction);
	    	}
	    }
	    catch(Exception e) {}
	    
	    return history;
	}
	
	public boolean addRating(User user, Game game, int rating)
	{
		if(user == null || rating < 1 || rating > 5)
			return false;
		
		Statement stmt = null;
		ResultSet rs = null;
		boolean isRated = false;
		
		String sql = "SELECT COUNT(*) as numPurchases, " + 
				"COUNT(rating) as numRatings, " + 
				"rating.ratingId " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON cartgame.gameId = game.gameID " + 
				"INNER JOIN cart " + 
				"ON cart.cartId = cartgame.cartId " + 
				"LEFT JOIN rating " + 
				"ON game.gameID = rating.gameId " + 
				"AND cart.customerId = rating.customerId " + 
				"WHERE cart.customerId = " + user.getID() + " " + 
				"AND game.gameID = " + game.getID();
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				int numPurchases = rs.getInt("numPurchases");
				int numRatings = rs.getInt("numRatings");
				int ratingID = rs.getInt("ratingId");
				
				if(numPurchases > 0)
				{
					if(numRatings == 0)
					{
						sql = "INSERT INTO rating " + 
								"(ratingId, gameId, customerId, rating) " + 
								"VALUES (NULL, " + game.getID() + ", " + 
								user.getID() + ", " + rating + ")";
					}
					else
					{
						sql = "UPDATE rating " + 
								"SET rating = " + rating + " " + 
								"WHERE ratingId = " + ratingID;
					}
					
					int affectedRows = stmt.executeUpdate(sql);
					
					isRated = (affectedRows == 0 ? false : true);
				}
			}
		}
		catch(Exception e) {}
		
		return isRated;
	}
	
	public float getTotalProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float totalProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as totalProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID = cartgame.gameId";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				totalProfit = rs.getFloat("totalProfit");
		}
		catch(Exception e) {}
		
		return totalProfit;
	}
	
	public float getPreviousWeekProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float previousWeekProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as previousWeekProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID = cartgame.gameId " + 
				"INNER JOIN cart " + 
				"ON cartgame.cartId = cart.cartId " + 
				"WHERE transactionDate >= CURRENT_DATE - INTERVAL DAYOFWEEK(CURRENT_DATE) + 6 DAY " + 
				"AND transactionDate < CURRENT_DATE - INTERVAL DAYOFWEEK(CURRENT_DATE) - 1 DAY";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				previousWeekProfit = rs.getFloat("previousWeekProfit");
		}
		catch(Exception e) {}
		
		return previousWeekProfit;
	}
	
	public float getCurrentWeekProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float currentWeekProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as currentWeekProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID = cartgame.gameId " + 
				"INNER JOIN cart " + 
				"ON cartgame.cartId = cart.cartId " + 
				"WHERE YEARWEEK(cart.transactionDate, 0) = YEARWEEK(CURRENT_DATE, 0)";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				currentWeekProfit = rs.getFloat("currentWeekProfit");
		}
		catch(Exception e) {}
		
		return currentWeekProfit;
	}
	
	public float getCurrentMonthProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float currentMonthProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as currentMonthProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID = cartgame.gameId " + 
				"INNER JOIN cart " + 
				"ON cartgame.cartId = cart.cartId " + 
				"WHERE YEAR(transactionDate) = YEAR(CURRENT_DATE()) " + 
				"AND MONTH(transactionDate) = MONTH(CURRENT_DATE())";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				currentMonthProfit = rs.getFloat("currentMonthProfit");
		}
		catch(Exception e) {}
		
		return currentMonthProfit;
	}
	
	public float getPreviousMonthProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float previousMonthProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as previousMonthProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID =  cartgame.gameId " + 
				"INNER JOIN cart " + 
				"ON cartgame.cartId = cart.cartId " + 
				"WHERE YEAR(transactionDate) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " + 
				"AND MONTH(transactionDate) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				previousMonthProfit = rs.getFloat("previousMonthProfit");
		}
		catch(Exception e) {}
		
		return previousMonthProfit;
	}
	
	public float getCurrentAnnualProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float currentAnnualProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as currentAnnualProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID = cartgame.gameId " + 
				"INNER JOIN cart " + 
				"ON cartgame.cartId = cart.cartId " + 
				"WHERE YEAR(transactionDate) = YEAR(CURRENT_DATE())";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				currentAnnualProfit = rs.getFloat("currentAnnualProfit");
		}
		catch(Exception e) {}
		
		return currentAnnualProfit;
	}
	
	public float getPreviousAnnualProfit()
	{
		Statement stmt = null;
		ResultSet rs = null;
		float previousAnnualProfit = 0f;
		
		String sql = "SELECT SUM(game.gamePrice) as previousAnnualProfit " + 
				"FROM game " + 
				"INNER JOIN cartgame " + 
				"ON game.gameID = cartgame.gameId " + 
				"INNER JOIN cart " + 
				"ON cartgame.cartId = cart.cartId " + 
				"WHERE YEAR(transactionDate) = YEAR(CURRENT_DATE()) - 1";
		
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
				previousAnnualProfit = rs.getFloat("previousAnnualProfit");
		}
		catch(Exception e) {}
		
		return previousAnnualProfit;
	}
	
	
}
