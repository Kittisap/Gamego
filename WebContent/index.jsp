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

<!DOCTYPE html>
<!-- This site was created in Webflow. http://www.webflow.com-->
<!-- Last Published: Fri Mar 06 2015 04:31:06 GMT+0000 (UTC) -->
<html data-wf-site="54eb722519500e4473ec8320" data-wf-page="54eb722519500e4473ec8321">
<head>
  <meta charset="utf-8">
  <title>Game Go!</title>
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
  <!-- <script type="text/javascript" src="js/modernizr.js"></script> -->
  <link rel="shortcut icon" type="image/x-icon" href="https://daks2k3a4ib2z.cloudfront.net/img/favicon.ico">
  <link rel="apple-touch-icon" href="https://daks2k3a4ib2z.cloudfront.net/img/webclip.png">
</head>
<body>
  <div class="w-nav navbar main-navbar" data-collapse="medium" data-animation="default" data-duration="400" data-contain="1">
    <div class="w-container">
      <a class="w-nav-brand brand-block" href="index.html"><img class="gamego-image" src="images/game go logo.png" width="214" height="62" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
      </a>
      <nav class="w-nav-menu" role="navigation"><a class="w-nav-link nav-link-login" href="login.html"><strong>Login</strong></a><a class="w-nav-link nav-link-signup" href="signup.html">Signup</a><a class="w-nav-link nav-link-cart" href="cart.jsp">Cart</a>
      </nav>
      <div class="w-nav-button">
        <div class="w-icon-nav-menu"></div>
      </div>
    </div>
  </div>
  <div class="w-container search-container">
    <div class="w-form">
      <form class="w-clearfix search-bar-form" id="email-form" name="email-form" data-name="Email Form" method="get" action="search.jsp">
        <input class="w-input search-bar-field" id="SearchItem" type="text" placeholder="Search for a game..." name="q" data-name="SearchItem" autofocus="autofocus">
        <input class="w-button search-bar-button" type="submit" value="Search" data-wait="Please wait...">
      </form>
      <div class="w-form-done">
        <p>Thank you! Your submission has been received!</p>
      </div>
      <div class="w-form-fail">
        <p>Oops! Something went wrong while submitting the form :(</p>
      </div>
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
    <div class="bi-weekly-favorites-text"><strong>Bi-weekly Favorites</strong>
    </div>
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
  <div class="w-section w-clearfix footer"><img class="footer-logo" src="images/game go logo.png" width="169" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
    <div class="footer-text">Copyright 2015. All rights reserved.&nbsp;All trademarks&nbsp;are property of their respective owners in the US and other countries</div>
  </div>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <!-- <script type="text/javascript" src="js/webflow.js"></script> -->
  <!--[if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]-->
</body>
</html>