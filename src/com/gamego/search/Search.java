package com.gamego.search;

import java.util.*;
import com.gamego.db.*;
import com.gamego.game.*;

public class Search
{
	private String m_query;
	private Vector<Game> m_results;
	
	public Search()
	{
		m_query = "";
		m_results = new Vector<Game>();
	}
	
	public Search(String query)
	{
		this();

		setQuery(query);
	}
	
	public boolean setQuery(String query)
	{
		m_query = query;
		
		return true;
	}
	
	public String getQuery()
	{
		return m_query;
	}
	
	public void performSearch()
	{
		Database db = new Database();
	}
}
