<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>

<%
final int GAME_GENRES = 5;
final int GAMES_SHOWN = 5;
String url = "jdbc:mysql://localhost:3306/gamego";
String username = "root";
String passwd = "admin";
DBConnectionPool connPool = new DBConnectionPool(url, username, passwd);
Connection conn = connPool.getConnection();
Statement stmt = conn.createStatement();
%>


<!DOCTYPE html>
<!-- This site was created in Webflow. http://www.webflow.com-->
<!-- Last Published: Fri Mar 06 2015 04:31:06 GMT+0000 (UTC) -->
<html data-wf-site="54eb722519500e4473ec8320" data-wf-page="54ece40faa70c2702274b1d0">
<head>
  <meta charset="utf-8">
  <title>Best Selling Categories - Game Go!</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="generator" content="Webflow">
  <link rel="stylesheet" type="text/css" href="css/normalize.css">
  <link rel="stylesheet" type="text/css" href="css/webflow.css">
  <link rel="stylesheet" type="text/css" href="css/game-go.webflow.css">
  <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.4.7/webfont.js"></script>
  <script>
    WebFont.load({
      google: {
        families: ["Exo:100,100italic,200,200italic,300,300italic,400,400italic,500,500italic,600,600italic,700,700italic,800,800italic,900,900italic","Bitter:400,700,400italic","Ubuntu:300,300italic,400,400italic,500,500italic,700,700italic","Lato:100,100italic,300,300italic,400,400italic,700,700italic,900,900italic","Droid Sans:400,700"]
      }
    });
  </script>
  <script type="text/javascript" src="js/modernizr.js"></script>
  <link rel="shortcut icon" type="image/x-icon" href="https://daks2k3a4ib2z.cloudfront.net/img/favicon.ico">
  <link rel="apple-touch-icon" href="https://daks2k3a4ib2z.cloudfront.net/img/webclip.png">
</head>
<body>
  <div class="w-nav navbar main-navbar" data-collapse="medium" data-animation="default" data-duration="400" data-contain="1">
    <div class="w-container">
      <a class="w-nav-brand brand-block" href="index.html"><img class="gamego-image" src="images/game go logo.png" width="214" height="62" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
      </a>
      <nav class="w-nav-menu" role="navigation"><a class="w-nav-link nav-link-login" href="login.html"><strong>Login</strong></a><a class="w-nav-link nav-link-signup" href="signup.html">Signup</a><a class="w-nav-link nav-link-cart" href="cart.html">Cart</a>
      </nav>
      <div class="w-nav-button">
        <div class="w-icon-nav-menu"></div>
      </div>
    </div>
  </div>
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
    		%>
    		<div class="w-col w-col-3 <%= genreList.get(i) %>-col">
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
    <div class="w-row">
      
    </div>
  </div>
  <div class="w-section w-clearfix footer"><img class="footer-logo" src="images/game go logo.png" width="169" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
    <div class="footer-text">Copyright 2015. All rights reserved.&nbsp;All trademarks&nbsp;are property of their respective owners in the US and other countries</div>
  </div>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script type="text/javascript" src="js/webflow.js"></script>
  <!--[if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]-->
</body>
</html>