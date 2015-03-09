package com.gamego.search;

import java.util.*;
import com.gamego.game.*;

public class Search
{
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_LIMIT = 10;
	public static final int DEFAULT_GENRE_ID = 0;

	private String m_query;
	private int m_genreID;
	private Vector<Game> m_results;
	private int m_totalResults;
	private int m_currentPage;
	private int m_offset;
	private int m_limit;
	private int m_totalPages;
	private int m_resultStart;
	private int m_resultEnd;
	
	public Search()
	{
		m_query = "";
		m_genreID = DEFAULT_GENRE_ID;
		m_results = new Vector<Game>();
		m_totalResults = 0;
		m_currentPage = DEFAULT_PAGE;
		m_offset = 0;
		m_limit = DEFAULT_LIMIT;
		m_totalPages = DEFAULT_PAGE;
		m_resultStart = 0;
		m_resultEnd = 0;
	}
	
	public Search(String query, int genreID)
	{
		this();

		setQuery(query);
		setGenreID(genreID);
	}
	
	public boolean setQuery(String query)
	{
		if(query == null)
			return false;

		m_query = query.trim();
		
		return true;
	}
	
	public String getQuery()
	{
		return m_query;
	}
	
	public boolean setGenreID(int genreID)
	{
		if(genreID < DEFAULT_GENRE_ID)
			return false;
		
		m_genreID = genreID;
		
		return true;
	}
	
	public int getGenreID()
	{
		return m_genreID;
	}
	
	public boolean setTotalResults(int totalResults)
	{
		if(totalResults < 0)
			return false;
		
		m_totalResults = totalResults;
		
		if(m_limit > 0)
			m_totalPages = (int)Math.ceil(m_totalResults / (float)m_limit);
		
		return true;
	}
	
	public int getTotalResults()
	{
		return m_totalResults;
	}
	
	public boolean setCurrentPage(int page)
	{
		if(page < DEFAULT_PAGE)
			page = DEFAULT_PAGE;
		else if(page > m_totalPages)
			page = m_totalPages;
		
		m_currentPage = page;
		m_offset = (page - 1) * m_limit;
		m_resultStart = m_offset + 1;
		m_resultEnd = m_offset + m_limit;
		
		if(m_resultEnd > m_totalResults)
			m_resultEnd = m_totalResults;
		
		return true;
	}
	
	public int getCurrentPage()
	{
		return m_currentPage;
	}
	
	public int getOffset()
	{
		return m_offset;
	}
	
	public int getLimit()
	{
		return m_limit;
	}
	
	public int getTotalPages()
	{
		return m_totalPages;
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
	
	public String getResultsHTML()
	{
		String html = "";

		if(m_results != null && m_results.size() > 0)
		{
			try
			{
				html += "<div class=\"searchMargin\">Displaying page " + m_currentPage + " of " + m_totalPages + ".</div>";
				html += "<div class=\"searchMargin\">Displaying results " + m_resultStart + " to " + m_resultEnd + " of " + m_totalResults + ".</div><br />";
				html += "<div class=\"searchResults\">";
				html += "<ul>";

				for(int i = 0; i < m_results.size(); i++)
				{
					html += "<li><a href=\"./game.jsp?id=" + 
							m_results.elementAt(i).getID() + "\">" + 
							m_results.elementAt(i).getShortTitle() + "</a></li>";
				}
				
				html += "</ul>";
				html += "</div><br />";
			}
			catch(Exception e) {}
		}
		else
		{
			html += "<div class=\"searchMargin\">No results found for \"" + m_query + "\".</div>";
		}
		
		return html;
	}
	
	public String getPageLinksHTML()
	{
		String firstPageHTML = "";
		String previousPageHTML = "";
		String nextPageHTML = "";
		String lastPageHTML = "";
		
		if(m_results != null && m_results.size() > 0)
		{
			if(m_currentPage == 1)
			{
				firstPageHTML = "<a class=\"pure-button pure-button-disabled\" href=\"#\">First</a>";
				previousPageHTML = "<a class=\"pure-button pure-button-disabled\" href=\"#\">Previous</a>";
			}
			else
			{
				firstPageHTML = "<a class=\"pure-button pure-button-primary\" href=\"./search.jsp?q=" + m_query + "&genreID=" + m_genreID + "&page=1\">First</a>";
				previousPageHTML = "<a class=\"pure-button pure-button-primary\" href=\"./search.jsp?q=" + m_query + "&genreID=" + m_genreID + "&page=" + (m_currentPage - 1) + "\">Previous</a>";
			}
			
			if(m_currentPage == m_totalPages)
			{
				nextPageHTML = "<a class=\"pure-button pure-button-disabled\" href=\"#\">Next</a>";
				lastPageHTML = "<a class=\"pure-button pure-button-disabled\" href=\"#\">Last</a>";
			}
			else
			{
				nextPageHTML = "<a class=\"pure-button pure-button-primary\" href=\"./search.jsp?q=" + m_query + "&genreID=" + m_genreID + "&page=" + (m_currentPage + 1) + "\">Next</a>";
				lastPageHTML = "<a class=\"pure-button pure-button-primary\" href=\"./search.jsp?q=" + m_query + "&genreID=" + m_genreID + "&page=" + m_totalPages + "\">Last</a>";
			}
		}

		String pageLinksHTML = "";
		
		pageLinksHTML += "<div class=\"searchMargin\">";
		pageLinksHTML += firstPageHTML;
		pageLinksHTML += previousPageHTML;
		pageLinksHTML += nextPageHTML;
		pageLinksHTML += lastPageHTML;
		pageLinksHTML += "</div>";
		
		return pageLinksHTML;
	}
}
