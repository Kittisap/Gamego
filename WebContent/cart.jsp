<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>
<%@ page import="com.gamego.cart.*" %>
<%@ page import="java.text.DecimalFormat" %>

<jsp:useBean id="cart" class="com.gamego.cart.CartBean" scope="session" />
<jsp:useBean id="user" class="com.gamego.user.User" scope="session" />
<%
Database db = new Database();
String[] idList = new String[0];
Game[] gameList = new Game[0];
float totalPrice = 0;

try
{
	cart.addGame(request.getParameter("gameId"));
	idList = cart.getCart();
	gameList = new Game[idList.length];
	for (int i = 0; i < gameList.length; i++)
	{
		gameList[i] = db.selectGame(Integer.parseInt(idList[i]));
	}
}
catch(Exception e){}
%>

<!DOCTYPE html>
<!-- This site was created in Webflow. http://www.webflow.com-->
<!-- Last Published: Fri Mar 06 2015 04:31:06 GMT+0000 (UTC) -->
<html data-wf-site="54eb722519500e4473ec8320" data-wf-page="54ef9b00669d98e13afd019e">
<head>
  <meta charset="utf-8">
  <title>Cart - Game Go!</title>
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
  <div class="w-container cart-container">
    <div class="your-cart-text"><strong>Your Cart</strong>
    </div>
    <ul class="w-list-unstyled list-of-cart-items">
    <%
    try
    {
        for (int i = 0; i < gameList.length; i++)
        {
        	totalPrice += gameList[i].getPrice();
        	if(gameList[i].getBoxArtPath().length() > 0)
        	%>
        	<li class="w-clearfix"><img class="cart-item-image-example" src="<%=gameList[i].getBoxArtPath() %>">
        	<div class="cart-item-name-example"><%=gameList[i].getTitle() %></div>
        	<div class="cart-item-price-example">$<%=gameList[i].getPrice() %></div>
        	</li>
        	<%
        }
    }
    catch(Exception e) {}
    DecimalFormat df = new DecimalFormat("#.00");
    String priceString = df.format(totalPrice);
    
    %>
    	<li class="w-clearfix">
    		<div class="cart-item-name-example">TOTAL PRICE: $<%=priceString %></div>
    	</li>
    </ul>
    <div class="w-form">
      <form class="w-clearfix" id="email-form-2" name="email-form-2" data-name="Email Form 2" type=POST action=cartServlet.jsp>
        <input class="w-button cart-checkout-button" type="submit" value="Purchase">
        <input type="hidden" name="amount" value="<%=priceString %>" />
      </form>
      <div class="w-form-done">
        <p>Thank you! Your submission has been received!</p>
      </div>
      <div class="w-form-fail">
        <p>Oops! Something went wrong while submitting the form :(</p>
      </div>
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