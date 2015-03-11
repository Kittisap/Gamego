package com.gamego.db;

import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;

import org.apache.commons.io.output.NullWriter;

import com.gamego.user.*;
import com.gamego.game.*;
import com.gamego.search.*;

public class Database
{
	private DBConnectionPool pool = null;
	private Connection conn = null;
	
	public Database()
	{
		try
		{
			String host = "jdbc:mysql://localhost:3306/gamego";
			String username = "root";
			String password = "admin";
			
			pool = new DBConnectionPool(host, username, password);
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
	
	public Boolean registerUser(User user)
	{
		if(conn == null || user == null || !user.isVerified())
			return false;
		
		Statement stmt = null;
		ResultSet rs = null;
		Boolean isRegistered = false;
		
		try
		{
			stmt = conn.createStatement();
			
			if(stmt != null)
			{
				String sql = "SELECT username " + 
						"FROM member " + 
						"WHERE username = '" + user.getUsername() + "'";

				rs = stmt.executeQuery(sql);
				
				if(rs != null && !rs.next())
				{
					sql = "INSERT INTO member " + 
							"(customerId, username, password, email) " + 
							"VALUES (NULL, '" + 
							user.getUsername() + "', '" + 
							user.getPassword() + "', '" + 
							user.getEmail() + "')";

					int rowsAffected = stmt.executeUpdate(sql);
					
					isRegistered = (rowsAffected > 0 ? true : false);
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
		
		return isRegistered;
	}
	
	public boolean verifyUser(User user)
	{
		if(conn == null || user == null)
			return false;
		
		Statement stmt = null;
		ResultSet rs = null;
		
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
					boolean isVerified = true;
					
					if(user.getPassword().equals(password))
					{	
						user.setID(id);
						user.setUsername(username);
						user.setPassword(password);
						user.setEmail(email);
						user.setDateRegistered(dateRegisterd);
						user.setAdmin(isAdmin);
						user.setVerified(isVerified);
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
		
		return user.isVerified();
	}
	
	public void addCart(String[] gameIds, int customerId)
	{
		Statement stmt = null;
		ResultSet result = null;
		String query;
		int cartId = 0;
		try 
		{
			stmt = conn.createStatement();
			query = "SELECT cartId ";
			query += "FROM cart ";
			query += "WHERE customerId=" + customerId;
			
			result = stmt.executeQuery(query);
			if(result.next())
			{
				cartId = result.getInt(1);
			}
			else
			{
				query = "INSERT INTO cart ";
				query += "VALUES(null,"+ customerId +",null) ";
				stmt.executeUpdate(query);
				query = "SELECT cartId ";
				query += "FROM cart ";
				query += "WHERE customerId=" + customerId;
				result = stmt.executeQuery(query);
				if(result.next())
				{
					cartId = result.getInt(1);
				}
			}
			if (cartId == 0)
			{
				return;
			}
			for (int i = 0; i < gameIds.length; i++)
			{
				query = "INSERT INTO cartgame ";
				query += "VALUES("+ cartId +"," + gameIds[i] + ")";
				stmt.executeUpdate(query);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
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
	
	public Search performSearch(String query)
	{
		return performSearch(query, Search.DEFAULT_GENRE_ID, 
				Search.DEFAULT_PAGE);
	}
	
	public Search performSearch(String query, String genreID, String page)
	{
		int numericGenreID = Search.DEFAULT_GENRE_ID;
		int numericPage = Search.DEFAULT_PAGE;
		
		try
		{
			if(genreID != null)
				numericGenreID = Integer.parseInt(genreID);
			
			if(page != null)
				numericPage = Integer.parseInt(page);
		}
		catch(Exception e) {}
		
		return performSearch(query, numericGenreID, numericPage);
	}
	
	public Search performSearch(String query, int genreID, int page)
	{
		if(conn == null)
			return null;
		
		Search search = null;
		Statement stmt = null;
		ResultSet countRS = null;
		ResultSet searchRS = null;
		
		try
		{
			search = new Search(query, genreID);
			stmt = conn.createStatement();
			
			if(stmt != null && !search.getQuery().isEmpty())
			{	
				String sql = "";
				
				if(search.getGenreID() == Search.DEFAULT_GENRE_ID)
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
				
				if(search.getGenreID() == Search.DEFAULT_GENRE_ID)
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
	
	public String getGenreDropdownHTML(int selectedGenreID)
	{
		String html = "";
		
		Statement stmt = null;
		ResultSet rs = null;
		
		if(conn != null)
		{
			try
			{
				stmt = conn.createStatement();
				
				if(stmt != null)
				{
					int genreID = 0;
					String genreName = "";
					
					html = "<div class=\"searchMargin\">";
					html += "<label for=\"genre\">Genre</label>";
					html += "<select id=\"genre\" name=\"genreID\">";
					html += "<option value=\"0\">All</option>";

					String sql = "SELECT * " + 
							"FROM genre " + 
							"ORDER BY genreName";

					rs = stmt.executeQuery(sql);
					
					while(rs.next())
					{
						genreID = rs.getInt("genreID");
						genreName = rs.getString("genreName");
						
						if(genreID == selectedGenreID)
							html += "<option value=\"" + genreID + "\" selected>" + genreName + "</option>";
						else
							html += "<option value=\"" + genreID + "\">" + genreName + "</option>";
					}
					
					html += "</select>";
					html += "</div>";
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
		}
		
		return html;
	}
	
	public String getHistoryHTML(int userID)
	{
		String html = "";
		Statement stmt = null;
		ResultSet rs = null;
		
	    String sql = "SELECT * " + 
	    		"FROM game " + 
	    		"INNER JOIN cartgame " + 
	    		"ON cartgame.gameId=game.gameId " + 
	    		"INNER JOIN cart " + 
	    		"ON cart.cartId=cartgame.cartId " + 
	    		"WHERE customerId = " + userID;
	    
	    try
	    {
	    	stmt = conn.createStatement();
	    	rs = stmt.executeQuery(sql);
	    	
	    	if(rs != null)
	    	{
	    		html += "<div class=\"searchMargin\">";
	    		html += "<ul class=\"w-list-unstyled list-of-cart-items\">";
	    		
	    		while(rs.next())
	    		{
	    			int gameID = rs.getInt("gameID");
	    			Game game = selectGame(gameID);
	    			Date datePurchased = rs.getDate("transactionDate");
	    			
	    			if(game != null)
	    			{
		            	html += "<li class=\"w-clearfix\">";
		            	html += "<img class=\"cart-item-image-example\" src=\"" + game.getBoxArtPath() + "\" />";
		            	html += "<div class=\"cart-item-name-example\">" + game.getTitle() + "</div>";
		            	html += "<div class=\"cart-item-price-example\">$" + game.getPrice() + "</div><br />";
		            	html += "<div>Purchased: " + datePurchased.toString() + "</div>";
		            	html += "</li>";
	    			}
	    		}
	    		
	    		html += "</ul>";
	    		html += "</div>";
	    	}
	    }
	    catch(Exception e) {}
	    
	    return html;
	}
}
