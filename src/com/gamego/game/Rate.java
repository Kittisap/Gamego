package com.gamego.game;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.Database;
import com.gamego.user.User;

@WebServlet("/login")
public class Rate extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public Rate()
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
		if(!User.isLoggedIn(request))
		{
			String gameIDStr = request.getParameter("gameID");
			String ratingStr = request.getParameter("rating");
			
			try
			{
				int gameID = Integer.parseInt(gameIDStr);
				int rating = Integer.parseInt(ratingStr);
				
				Database db = new Database();
				User user = User.getSessionUser(request);
				
				if(db != null && user != null)
				{
					db.addRating(user, gameID, rating);
					
					response.sendRedirect("./history");
				}
			}
			catch(Exception e) {}
		}
		else
		{
			response.sendRedirect("./index.jsp");
		}
	}
}
