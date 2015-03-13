package com.gamego.util;

import java.text.DecimalFormat;

public class Utilities
{
	public Utilities() {}
	
	public static String formatMoney(float amount)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		String formattedAmount = "";
		
		if(amount < 0f)
			formattedAmount += "-";
		
		formattedAmount += "$" + df.format(Math.abs(amount));
		
		return formattedAmount;
	}
}
