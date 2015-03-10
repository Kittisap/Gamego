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
		if(User.isLoggedIn(request))
			response.sendRedirect("./index.jsp");

		if(request != null && response != null)
		{
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			
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
			String passwordConfirm = request.getParameter("passwordConfirm");
			String email = request.getParameter("email");
			
			Database db = new Database();
			User user = new User(username, password, passwordConfirm, email);
			
			if(db != null && db.registerUser(user))
			{
				RequestDispatcher rd = request.getRequestDispatcher("registerSuccess.jsp");
				
				if(rd != null)
				{
					response.setContentType("text/html");
					
					rd.include(request, response);
				}
			}
			else
			{
				response.sendRedirect("./register?error");
			}
		}
	}
}
