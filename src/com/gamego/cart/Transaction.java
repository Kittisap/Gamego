package com.gamego.cart;

import java.text.DecimalFormat;
import java.util.*;

import com.gamego.util.Utilities;

public class Transaction
{
	private int m_userID;
	private int m_transactionID;
	private Date m_transactionDate;
	private Vector<TransactionItem> m_items;
	private float m_orderTotal;
	
	public Transaction()
	{
		m_userID = 0;
		m_transactionID = 0;
		m_transactionDate = new Date();
		m_items = new Vector<TransactionItem>();
		m_orderTotal = 0f;
	}

	public Transaction(int userID, int transactionID, Date transactionDate)
	{
		this();
		
		setUserID(userID);
		setTransactionID(transactionID);
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
	
	public boolean setTransactionID(int transactionID)
	{
		if(transactionID <= 0)
			return false;
		
		m_transactionID = transactionID;
		
		return true;
	}
	
	public int getTransactionID()
	{
		return m_transactionID;
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
	
	public boolean addItem(TransactionItem item)
	{
		if(item == null)
			return false;
		
		m_items.add(item);
		m_orderTotal += item.getItem().getPrice();
		
		return true;
	}
	
	public Vector<TransactionItem> getItems()
	{
		return m_items;
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
		return Utilities.formatMoney(m_orderTotal);
	}
	
	public static String formatTransactionID(int transactionID)
	{
		DecimalFormat df = new DecimalFormat("00000000000");
		
		return "#" + df.format(transactionID);
	}
}
