package com.gamego.cart;

import com.gamego.game.*;

public class TransactionItem
{
	private Game m_item;
	private int m_rating;
	
	public TransactionItem()
	{
		m_item = null;
		m_rating = 0;
	}
	
	public TransactionItem(Game item, int rating)
	{
		this();
		
		setItem(item);
		setRating(rating);
	}
	
	public boolean setItem(Game item)
	{
		if(item == null)
			return false;
		
		m_item = item;
		
		return true;
	}
	
	public Game getItem()
	{
		return m_item;
	}
	
	public boolean setRating(int rating)
	{
		if(rating < 1 || rating > 5)
			return false;
		
		m_rating = rating;
		
		return true;
	}
	
	public int getRating()
	{
		return m_rating;
	}
}
