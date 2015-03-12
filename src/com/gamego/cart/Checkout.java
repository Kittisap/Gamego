package com.gamego.cart;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.Database;
import com.gamego.user.User;

@WebServlet("/checkout")
public class Checkout extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public Checkout()
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
			Database db = new Database();
			User user = User.getSessionUser(request);
			CartBean cart = (CartBean)request.getSession().getAttribute("cart");
			int transactionID = db.addCart(cart.getCart(), user.getID());
			
			if(transactionID != 0)
			{
				cart.clear();
				
				request.setAttribute("transactionID", Transaction.formatTransactionID(transactionID));
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp");
				
				rd.include(request, response);
			}
			else
			{
				response.sendRedirect("./cart.jsp");
			}
		}
		else
		{
			response.sendRedirect("./login");
		}
	}
}
