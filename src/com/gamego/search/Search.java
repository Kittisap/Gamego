package com.gamego.search;

import java.util.*;
import com.gamego.game.*;

public class Search
{
	private String m_query;
	private Vector<Game> m_results;
	
	public Search()
	{
		m_query = "None";
		m_results = new Vector<Game>();
	}
	
	public Search(String query)
	{
		this();

		setQuery(query);
	}
	
	public boolean setQuery(String query)
	{
		if(query == null)
			return false;

		m_query = query;
		
		return true;
	}
	
	public String getQuery()
	{
		return m_query;
	}
	
	public boolean addResult(Game game)
	{
		if(game == null)
			return false;
		
		m_results.add(game);
		
		return true;
	}
	
	public int getResultCount()
	{
		if(m_results == null)
			return 0;
		
		return m_results.size();
	}
	
	public String getResultHTML()
	{
		String html = "None";

		if(m_results != null && m_results.size() > 0)
		{
			try
			{
				html = "<ul>";
				
				for(int i = 0; i < m_results.size(); i++)
				{
					html += "<li><a href=\"./game.jsp?id=" + 
							m_results.elementAt(i).getID() + "\">" + 
							m_results.elementAt(i).getTitle() + "</a></li>";
				}
				
				html += "</ul>";
				
				return html;
			}
			catch(Exception e) {}
		}
		
		return html;
	}
}
