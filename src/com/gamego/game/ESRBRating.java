package com.gamego.game;

public class ESRBRating
{
	private int m_id;
	private String m_name;
	private String m_alias;
	
	public ESRBRating()
	{
		m_id = 0;
		m_name = "N/A";
		m_alias = "N/A";
	}
	
	public ESRBRating(int id, String name, String alias)
	{
		this();
		
		setID(id);
		setName(name);
		setAlias(alias);
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
	
	public boolean setAlias(String alias)
	{
		if(alias == null)
			return false;
		
		m_alias = alias;
		
		return true;
	}
	
	public String getAlias()
	{
		return m_alias;
	}
	
	public String toString()
	{
		return m_alias + " (" + m_name + ")";
	}
}
