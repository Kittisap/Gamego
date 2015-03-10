package com.gamego.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.Database;

@WebServlet("/login")
public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public Login()
    {
    	super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		if(User.isLoggedIn(request))
			response.sendRedirect("./index.jsp");
		
		if(request != null && response != null)
		{
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			
			if(rd != null)
			{
				response.setContentType("text/html");
				
				rd.include(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		if(User.isLoggedIn(request))
			response.sendRedirect("./index.jsp");
		
		if(request != null && response != null)
		{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			Database db = new Database();
			User user = new User(username, password);
			
			if(db != null && db.verifyUser(user))
			{
				request.getSession().setAttribute("user", user);
				response.sendRedirect("./index.jsp");
			}
			else
			{
				response.sendRedirect("./login?error");
			}
		}
	}
}
