package com.gamego.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.*;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Register()
    {
    	super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		if(!User.isLoggedIn(request))
		{
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
			
			if(rd != null)
				rd.include(request, response);
		}
		else
		{
			response.sendRedirect("./index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		if(!User.isLoggedIn(request))
		{
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String passwordConfirm = request.getParameter("passwordConfirm");
			
			Database db = new Database();
			
			try
			{
				User user = new User(username, email, password, passwordConfirm);
				
				db.registerUser(user);
			}
			catch(Exception e)
			{
				request.setAttribute("username", username);
				request.setAttribute("email", email);
				request.setAttribute("error", e.getMessage());
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
				
				rd.include(request, response);
			}
		}
		else
		{
			response.sendRedirect("./index.jsp");
		}
	}
}
