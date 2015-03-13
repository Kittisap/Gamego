package com.gamego.search;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.Database;

@WebServlet("/search")
public class Search extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public Search()
    {
    	super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		String query = request.getParameter("q");
		String genreID = request.getParameter("genreID");
		String page = request.getParameter("page");
		
		Database db = new Database();
		SearchResult searchResults = null;
		
		try
		{
			searchResults = db.performSearch(query, genreID, page);
			
			if(searchResults.getTotalResults() == 1)
			{
				response.sendRedirect("./product?id=" + searchResults.getResults().firstElement().getID());
				return;
			}

			request.setAttribute("searchQuery", query);
			request.setAttribute("searchResults", searchResults);
			request.setAttribute("genreID", genreID);
			request.setAttribute("genres", db.getGenres());
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
			
			rd.include(request, response);
		}
		catch(Exception e) {}
	}
}
