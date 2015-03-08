package com.gamego.game;

import java.util.*;

public class Game
{
	private int m_id;
	private String m_title;
	private String m_description;
	private Developer m_developer;
	private Publisher m_publisher;
	private Vector<Genre> m_genres;
	private String m_releaseDate;
	private ESRBRating m_esrbRating;
	private String m_boxArtPath;
	private int m_stock;
	private float m_price;
	
	public Game()
	{
		m_id = 0;
		m_title = "Unknown";
		m_description = "None";
		m_developer = new Developer();
		m_publisher = new Publisher();
		m_genres = new Vector<Genre>();
		m_releaseDate = "Unknown";
		m_esrbRating = new ESRBRating();
		m_boxArtPath = "https://d3e54v103j8qbb.cloudfront.net/img/image-placeholder.svg";
		m_stock = 0;
		m_price = 0.0f;
		
	}
	
	public Game(int id, String title, String description, Developer developer, 
			Publisher publisher, Vector<Genre> genres, String releaseDate, 
			ESRBRating esrbRating, String boxArtPath, int stock, float price)
	{
		this();
		
		setID(id);
		setTitle(title);
		setDescription(description);
		setDeveloper(developer);
		setPublisher(publisher);
		setGenres(genres);
		setReleaseDate(releaseDate);
		setESRBRating(esrbRating);
		setBoxArtPath(boxArtPath);
		setStock(stock);
		setPrice(price);
	}
	
	public boolean setID(int id)
	{
		if(id <= 0)
			return false;
		
		m_id = id;
		
		return true;
	}
	
	public int getID()
	{
		return m_id;
	}
	
	public boolean setTitle(String title)
	{
		if(title == null)
			return false;

		m_title = title;
		
		return true;
	}
	
	public String getTitle()
	{
		return m_title;
	}
	
	public boolean setDescription(String description)
	{
		if(description == null)
			return false;
		
		m_description = description;
		
		return true;
	}
	
	public String getDescription()
	{
		return m_description;
	}
	
	public boolean setDeveloper(Developer developer)
	{
		if(developer == null)
			return false;
		
		m_developer = developer;
		
		return true;
	}
	
	public Developer getDeveloper()
	{
		return m_developer;
	}
	
	public boolean setPublisher(Publisher publisher)
	{
		if(publisher == null)
			return false;
		
		m_publisher = publisher;
		
		return true;
	}
	
	public Publisher getPublisher()
	{
		return m_publisher;
	}
	
	public boolean setGenres(Vector<Genre> genres)
	{
		if(genres == null)
			return false;
		
		m_genres = genres;
		
		return true;
	}
	
	public Vector<Genre> getGenres()
	{
		return m_genres;
	}
	
	public String getGenreString()
	{
		String genreString = "Unknown";
		
		if(m_genres.size() > 0)
		{
			genreString = "";
			
			for(int i = 0; i < m_genres.size(); i++)
				genreString += m_genres.elementAt(i).getName() + " / ";
			
			genreString = genreString.substring(0, genreString.length() - 3);
		}
		
		return genreString;
	}
	
	public boolean setReleaseDate(String releaseDate)
	{
		if(releaseDate == null)
			return false;
		
		m_releaseDate = releaseDate;
		
		return true;
	}
	
	public String getReleaseDate()
	{
		return m_releaseDate;
	}
	
	public boolean setESRBRating(ESRBRating esrbRating)
	{
		if(esrbRating == null)
			return false;
		
		m_esrbRating = esrbRating;
		
		return true;
	}
	
	public ESRBRating getESRBRating()
	{
		return m_esrbRating;
	}
	
	public boolean setBoxArtPath(String boxArtPath)
	{
		if(boxArtPath == null)
			return false;
		
		m_boxArtPath = "http://thegamesdb.net/banners/" + boxArtPath;
		
		return true;
	}
	
	public String getBoxArtPath()
	{
		return m_boxArtPath;
	}
	
	public boolean setStock(int stock)
	{
		if(stock < 0)
			return false;
		
		m_stock = stock;
		
		return true;
	}
	
	public int getStock()
	{
		return m_stock;
	}
	
	public boolean setPrice(float price)
	{
		if(price < 0.0f)
			return false;
		
		m_price = price;
		
		return true;
	}
	
	public float getPrice()
	{
		return m_price;
	}
}
