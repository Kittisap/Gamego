package com.gamego.cart;

import java.text.DecimalFormat;
import java.util.*;

import com.gamego.game.*;

public class Transaction
{
	private int m_userID;
	private Vector<Game> m_items;
	private Date m_transactionDate;
	private float m_orderTotal;
	
	public Transaction()
	{
		m_userID = 0;
		m_items = new Vector<Game>();
		m_transactionDate = new Date();
		m_orderTotal = 0f;
	}

	public Transaction(int userID, Date transactionDate)
	{
		this();
		
		setUserID(userID);
		setTransactionDate(transactionDate);
	}
	
	public boolean setUserID(int userID)
	{
		if(userID <= 0)
			return false;
		
		m_userID = userID;
		
		return true;
	}
	
	public int getUserID()
	{
		return m_userID;
	}
	
	public boolean addItem(Game game)
	{
		if(game == null)
			return false;
		
		m_items.add(game);
		m_orderTotal += game.getPrice();
		
		return true;
	}
	
	public Vector<Game> getItems()
	{
		return m_items;
	}
	
	public boolean setTransactionDate(Date transactionDate)
	{
		if(transactionDate == null)
			return false;
		
		m_transactionDate = transactionDate;
		
		return true;
	}
	
	public Date getTransactionDate()
	{
		return m_transactionDate;
	}
	
	public int getItemCount()
	{
		if(m_items == null)
			return 0;
		
		return m_items.size();
	}
	
	public float getOrderTotal()
	{
		return m_orderTotal;
	}
	
	public String getFormattedOrderTotal()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		
		return "$" + df.format(m_orderTotal);
	}
}
