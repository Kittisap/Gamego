<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>

<%
final int TOP_GAMES = 10;
final int BIWEEKLY_GAMES = 5;
String url = "jdbc:mysql://localhost:3306/gamego";
String username = "root";
String passwd = "admin";
DBConnectionPool connPool = new DBConnectionPool(url, username, passwd);
Connection conn = connPool.getConnection();
Statement stmt = conn.createStatement();
String query;
ResultSet gameSet;
%>

<jsp:include page="header.jsp" />

  <div class="w-container search-container">
    <div class="w-form">
      <form class="w-clearfix search-bar-form" id="email-form" name="email-form" data-name="Email Form" method="get" action="search.jsp">
        <input class="w-input search-bar-field" id="SearchItem" type="text" placeholder="Search for a game..." name="q" data-name="SearchItem" autofocus="autofocus">
        <input class="w-button search-bar-button" type="submit" value="Search" data-wait="Please wait...">
      </form>
    </div>
  </div>
  <div class="w-container whats-hot-container">
    <div class="what-s-hot-text-block"><strong class="what-s-hot-text">What's <span class="hot-text">HOT</span></strong>
    </div>
    <div class="w-row">
	    <div class="w-col w-col-6">
	        <ol>
    <%
    query = "SELECT game.gameTitle, game.gameID ";
    query += "FROM game ";
    query += "INNER JOIN cartgame ON cartgame.gameID=game.gameID ";
	query += "GROUP BY game.gameTitle ";
	query += "ORDER BY count(game.gameTitle) DESC;";
    try
    {
    	gameSet = stmt.executeQuery(query);
    	for (int i = 0; i < TOP_GAMES/2; i++)
    	{
			if (gameSet.next())
			{
				%>
				
	   			<li><a href="game.jsp?id=<%=gameSet.getString(2) %>"><%=gameSet.getString(1) %></a></li>
	   			<%
			}
			else
				break;
    	}
    	%>
    		</ol>
    	</div>
    	<div class="w-col w-col-6">
    		<ol start="<%=TOP_GAMES/2+1 %>">
    	<%
    	for (int i = 0; i < TOP_GAMES/2; i++)
    	{
			if (gameSet.next())
			{
				%>
				
	   			<li><a href="game.jsp?id=<%=gameSet.getString(2) %>"><%=gameSet.getString(1) %></a></li>
	   			<%
			}
			else
				break;
    	}
    }
    catch(Exception e){}
    %>
            </ol>
      </div>
    </div>

    <a class="w-inline-block link-block-to-categories" href="best-selling-categories.jsp">
      <div>List of Best Selling Genres</div>
    </a>
  </div>
  <div class="w-container bi-weekly-favorite-container">
    <div class="bi-weekly-favorites-text"><strong>Bi-weekly Favorites</strong></div>
    <ol>
    <%
    query = "SELECT game.gameTitle, game.gameID ";
    query += "FROM game ";
    query += "INNER JOIN cartgame ON cartgame.gameID=game.gameID ";
    query += "INNER JOIN cart ON cart.cartId = cartgame.cartId ";
    query += "WHERE cart.transactionDate between date_sub(now(),INTERVAL 2 WEEK) and now() ";
	query += "GROUP BY game.gameTitle ";
	query += "ORDER BY count(game.gameTitle) DESC;";
	try
	{
		gameSet = stmt.executeQuery(query);
		for(int i = 0; i < BIWEEKLY_GAMES; i++)
		{
			if (gameSet.next())
			{
				%>
	   			<li><a href="game.jsp?id=<%=gameSet.getString(2) %>"><%=gameSet.getString(1) %></a></li>
	   			<%
			}
			else
				break;
		}
	}
	catch(Exception e){}

    %>
    </ol>
  </div>

<jsp:include page="footer.jsp" />
