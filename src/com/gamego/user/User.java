package com.gamego.user;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.*;

public class User
{
	private int m_id;
	private String m_username;
	private String m_password;
	private String m_email;
	private Date m_dateRegistered;
	private boolean m_isAdmin;
	private boolean m_isVerified;
	
	public User()
	{
		m_id = 0;
		m_username = null;
		m_password = null;
		m_email = null;
		m_dateRegistered = null;
		m_isAdmin = false;
		m_isVerified = false;
	}
	
	// Used for login.
	public User(String username, String password)
	{
		this();
		
		setUsername(username);
		setPassword(password);
	}
	
	// Used for registration.
	public User(String username, 
			String password, 
			String passwordConfirm, 
			String email)
	{
		this();
		
		setUsername(username);
		setEmail(email);
		
		if(password != null && passwordConfirm != null && 
				password.equals(passwordConfirm))
		{
			setPassword(password);
			setVerified(true);
		}
	}
	
	public boolean setID(int id)
	{
		if(id <= 0)
			return false;
		
		m_id = id;
		
		return true;
	}
	
	public int getID()
	{
		return m_id;
	}
	
	public boolean setUsername(String username)
	{
		if(username == null)
			return false;
		
		m_username = username;
		
		return true;
	}
	
	public String getUsername()
	{
		return m_username;
	}
	
	public boolean setPassword(String password)
	{
		if(password == null)
			return false;
		
		m_password = hash(password);
		
		return true;
	}
	
	public String getPassword()
	{
		return m_password;
	}
	
	public boolean setEmail(String email)
	{
		if(email == null)
			return false;
		
		m_email = email;
		
		return true;
	}
	
	public String getEmail()
	{
		return m_email;
	}
	
	public boolean setDateRegistered(Date dateRegistered)
	{
		if(dateRegistered == null)
			return false;
		
		m_dateRegistered = dateRegistered;
		
		return true;
	}
	
	public Date getDateRegistered()
	{
		return m_dateRegistered;
	}
	
	public void setAdmin(boolean isAdmin)
	{
		m_isAdmin = isAdmin;
	}
	
	public boolean isAdmin()
	{
		return m_isAdmin;
	}
	
	public void setVerified(boolean isVerified)
	{
		m_isVerified = isVerified;
	}
	
	public boolean isVerified()
	{
		return m_isVerified;
	}
	
	public static String hash(String str)
	{
		if(str == null)
			return null;
		
		return DigestUtils.sha256Hex(str);
	}
	
	public static boolean isLoggedIn(HttpServletRequest request)
	{
		if(request != null)
		{
			User user = getSessionUser(request);
			
			if(user != null)
				return true;
		}
		
		return false;
	}
	
	public static User getSessionUser(HttpServletRequest request)
	{
		if(request == null)
			return null;
		
		return (User)request.getSession().getAttribute("user");
	}
}
