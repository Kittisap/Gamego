package com.gamego.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamego.db.Database;
import com.gamego.util.Utilities;

@WebServlet("/admin")
public class Admin extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public Admin()
    {
    	super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		if(User.isLoggedIn(request))
		{
			User user = User.getSessionUser(request);
			
			if(user != null && user.isAdmin())
			{
				Database db = new Database();
				
				float totalProfit = db.getTotalProfit();
				float currentWeekProfit = db.getCurrentWeekProfit();
				float previousWeekProfit = db.getPreviousWeekProfit();
				float weekDifference = currentWeekProfit - previousWeekProfit;
				float currentMonthProfit = db.getCurrentMonthProfit();
				float previousMonthProfit = db.getPreviousMonthProfit();
				float monthDifference = currentMonthProfit - previousMonthProfit;
				float currentAnnualProfit = db.getCurrentAnnualProfit();
				float previousAnnualProfit = db.getPreviousAnnualProfit();
				float annualDifference = currentAnnualProfit - previousAnnualProfit;

				request.setAttribute("totalProfit", Utilities.formatMoney(totalProfit));
				request.setAttribute("currentWeekProfit", Utilities.formatMoney(currentWeekProfit));
				request.setAttribute("previousWeekProfit", Utilities.formatMoney(previousWeekProfit));
				request.setAttribute("weekDifference", Utilities.formatMoney(weekDifference));
				request.setAttribute("currentMonthProfit", Utilities.formatMoney(currentMonthProfit));
				request.setAttribute("previousMonthProfit", Utilities.formatMoney(previousMonthProfit));
				request.setAttribute("monthDifference", Utilities.formatMoney(monthDifference));
				request.setAttribute("currentAnnualProfit", Utilities.formatMoney(currentAnnualProfit));
				request.setAttribute("previousAnnualProfit", Utilities.formatMoney(previousAnnualProfit));
				request.setAttribute("annualDifference", Utilities.formatMoney(annualDifference));
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
				
				if(rd != null)
					rd.include(request, response);
			}
			else
			{
				response.sendRedirect("./index.jsp");
			}
		}
		else
		{
			response.sendRedirect("./login");
		}
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
