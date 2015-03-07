package com.gamego.user;

import org.apache.commons.codec.digest.*;

public class User
{
	private String m_username = null;
	private String m_password = null;
	
	public User()
	{
	}
	
	public User(String username, String password)
	{
		m_username = username;
		m_password = hash(password);
	}
	
	public User(String username, String password, String passwordConfirm)
	{
		m_username = username;
		
		if(password != null && passwordConfirm != null && password.equals(passwordConfirm))
			m_password = hash(password);
	}
	
	public String getUsername()
	{
		return m_username;
	}
	
	public String getPassword()
	{
		return m_password;
	}
	
	public Boolean isInitialized()
	{
		return m_username != null && m_password != null;
	}
	
	public static String hash(String str)
	{
		if(str == null)
			return null;
		
		return DigestUtils.sha256Hex(str);
	}
}
