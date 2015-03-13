package com.gamego.search;

import java.util.*;
import com.gamego.game.*;

public class SearchResult
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
	
	public SearchResult()
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
	
	public SearchResult(String query, int genreID)
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
	
	public Vector<Game> getResults()
	{
		return m_results;
	}
	
	public int getResultCount()
	{
		if(m_results == null)
			return 0;
		
		return m_results.size();
	}
	
	public int getResultStart()
	{
		return m_resultStart;
	}
	
	public int getResultEnd()
	{
		return m_resultEnd;
	}
}
