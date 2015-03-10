<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>

<%
final int GAME_GENRES = 6;
final int GAMES_SHOWN = 5;
String url = "jdbc:mysql://localhost:3306/gamego";
String username = "root";
String passwd = "admin";
DBConnectionPool connPool = new DBConnectionPool(url, username, passwd);
Connection conn = connPool.getConnection();
Statement stmt = conn.createStatement();
%>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Bestsellers by Genre" />
</jsp:include>

  <div class="w-container categories-container-1">
    <h1>Bestsellers by Genre</h1>
    <div class="w-row">
    <%
    Vector<String> genreList = new Vector<String>();
    Vector<Integer> genreID = new Vector<Integer>();
    String genreQuery;
    genreQuery = "SELECT genre.genreName, genre.genreID ";
    genreQuery += "FROM cartgame ";
    genreQuery += "JOIN gamegenre ON gamegenre.gameID=cartgame.gameId ";
    genreQuery += "JOIN genre ON gamegenre.genreID=genre.genreID ";
    genreQuery += "GROUP BY gamegenre.genreID ";
    genreQuery += "ORDER BY count(gamegenre.genreID) desc";
    
    try
    {
  		ResultSet genres = stmt.executeQuery(genreQuery);
    	for(int i = 0; i < GAME_GENRES; i++)
    	{
    		if (genres.next())
    		{
    			genreList.add(genres.getString("genreName"));
    			genreID.add(genres.getInt("genreID"));
    		}
    		else
    			break;
    	}
    	genres.close();
    	
    	String gameQuery;
    	ResultSet gameSet;
    	for(int i = 0; i < genreList.size(); i++)
    	{
    		if (i == 3)
    		{
    			%>
    			</div>
    			<div class="w-row">
    			<%
    		}
    		%>
    		<div class="w-col w-col-4 <%= genreList.get(i) %>-col">
        		<h1 class="category-heading"><%= genreList.get(i) %></h1>
        		<ol>
        	<%
        	gameQuery = "SELECT game.gameTitle, game.gameID ";
        	gameQuery += "FROM game ";
        	gameQuery += "INNER JOIN cartgame ON cartgame.gameID=game.gameID ";
        	gameQuery += "INNER JOIN gamegenre ON gamegenre.gameID=game.gameID ";
        	gameQuery += "WHERE gamegenre.genreID=" + genreID.get(i) + " ";
        	gameQuery += "GROUP BY game.gameTitle ";
        	gameQuery += "ORDER BY count(game.gameTitle) DESC";
        	gameSet = stmt.executeQuery(gameQuery);
        	for (int j = 0; j < GAMES_SHOWN; j++)
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
	    <%
    	}

    }
    catch (Exception e){}
      
    %>
    </div>
    <div class="w-row"></div>
  </div>

<jsp:include page="footer.jsp" />
