package com.gamego.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		RequestDispatcher rd = request.getRequestDispatcher("login.html");
		
		if(rd != null)
		{
			response.setContentType("text/html");
			
			rd.include(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter o = response.getWriter();
		
		o.print("Logged in!");
	}
}
