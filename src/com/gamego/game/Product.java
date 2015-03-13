package com.gamego.game;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamego.db.Database;

@WebServlet("/product")
public class Product extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public Product()
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
		String gameID = request.getParameter("id");
		Database db = new Database();

		try
		{
			request.setAttribute("product", db.selectGame(Integer.parseInt(gameID)));
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
			
			rd.include(request, response);
		}
		catch(Exception e) {}
	}
}
