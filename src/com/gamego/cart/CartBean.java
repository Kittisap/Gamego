package com.gamego.cart;

import java.io.Serializable;
import java.util.*;

public class CartBean implements Serializable
{
	private Vector<String> cart = new Vector<String>();
	private String gameId;
	private String submit;
	
	public void setSubmit(String submit)
	{
		this.submit = submit;
	}
		
	public void addGame(String gameId)
	{
		if(!cart.contains(gameId))
		{
			try
			{
				Integer.parseInt(gameId);
				cart.add(gameId);
			}
			catch (Exception e){}
		}
	}
	
	public String[] getCart()
	{
		String[] arr = new String[cart.size()];
		for (int i = 0; i < cart.size(); i++)
		{
			arr[i] = cart.get(i);
		}
		return arr;		
	}
	
	public void clear()
	{
		cart.removeAllElements();
	}

}
