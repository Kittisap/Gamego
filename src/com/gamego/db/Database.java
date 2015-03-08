package com.gamego.db;

import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.gamego.user.*;
import com.gamego.game.*;

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
		if(conn == null || user == null || !user.isInitialized())
			return false;
		
		Statement stmt = null;
		ResultSet rs = null;
		Boolean isRegistered = false;
		
		try
		{
			stmt = conn.createStatement();
			
			if(stmt != null)
			{
				rs = stmt.executeQuery("SELECT username FROM users WHERE username = '" + user.getUsername() + "'");
				
				if(rs != null && !rs.next())
				{
					long timestamp = System.currentTimeMillis() / 1000L;
					int rowsAffected = stmt.executeUpdate("INSERT INTO users (userID, username, password, registeredTime, lastLoginTime) VALUES (NULL, '" + user.getUsername() + "', '" + user.getPassword() + "', " + timestamp + ", 0)");
					
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
	
	public Game selectGame(int gameID)
	{
		if(conn == null)
			return null;
		
		Game game = null;
		Statement stmt = null;
		ResultSet gameRS = null;
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
							esrbRating, gameBoxArtPath, gameStock, gamePrice);
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
				if(stmt != null)
					stmt.close();
			}
			catch(Exception e) {}
			
			close();
		}
		
		return game;
	}
}
