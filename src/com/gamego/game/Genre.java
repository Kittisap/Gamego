package com.gamego.game;

public class Genre
{
	private int m_id;
	private String m_name;
	
	public Genre()
	{
		m_id = 0;
		m_name = "Unknown";
	}
	
	public Genre(int id, String name)
	{
		this();
		
		setID(id);
		setName(name);
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
	
	public boolean setName(String name)
	{
		if(name == null)
			return false;
		
		m_name = name;
		
		return true;
	}
	
	public String getName()
	{
		return m_name;
	}
}
