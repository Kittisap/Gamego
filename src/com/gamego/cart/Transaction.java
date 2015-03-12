package com.gamego.cart;

import java.util.*;
import com.gamego.game.*;

public class Transaction
{
	private int m_userID;
	private Game m_game;
	private Date m_transactionDate;
	
	public Transaction()
	{
		m_userID = 0;
		m_game = null;
		m_transactionDate = null;
	}

	public Transaction(int userID, Game game, Date transactionDate)
	{
		this();
		
		setUserID(userID);
		setGame(game);
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
	
	public boolean setGame(Game game)
	{
		if(game == null)
			return false;
		
		m_game = game;
		
		return true;
	}
	
	public Game getGame()
	{
		return m_game;
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
}
