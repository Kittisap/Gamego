package com.gamego.game;

public class Publisher
{
	private int m_id;
	private String m_name;
	
	public Publisher()
	{
		m_id = 0;
		m_name = "N/A";
	}
	
	public Publisher(int id, String name)
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
