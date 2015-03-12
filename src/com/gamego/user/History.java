package com.gamego.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.Database;

@WebServlet("/history")
public class History extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public History()
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
		if(User.isLoggedIn(request))
		{
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/history.jsp");
			
			if(rd != null)
			{
				Database db = new Database();
				User user = User.getSessionUser(request);
				
				if(db != null && user != null)
				{
					request.setAttribute("transactions", db.getUserHistory(user.getID()));
					rd.include(request, response);
				}
			}
		}
		else
		{
			response.sendRedirect("index.jsp");
		}
	}
}
