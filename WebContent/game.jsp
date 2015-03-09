<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>
<%
	Game game = null;
	boolean hasError = true;

	String gameID = request.getParameter("id");
	
	if(gameID != null)
	{
		Database db = new Database();

		try
		{
			game = db.selectGame(Integer.parseInt(gameID));
			
			if(game != null)
				hasError = false;
		}
		catch(Exception e) {}
	}
	
	if(hasError)
	{
		response.sendRedirect("./index.html");
		
		return;
	}
%>
<!DOCTYPE html>
<!-- This site was created in Webflow. http://www.webflow.com-->
<!-- Last Published: Fri Mar 06 2015 04:31:06 GMT+0000 (UTC) -->
<html data-wf-site="54eb722519500e4473ec8320" data-wf-page="54ece185aa70c2702274b165">
<head>
  <meta charset="utf-8">
  <title>GameGo - <%=game.getTitle() %></title>
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
  <div class="w-container game-container">
    <div class="w-row game-row-1">
      <div class="w-col w-col-9 game-info-col-1">
        <h1><%=game.getTitle() %></h1><div class="game-image-container"><img class="game-image" src="<%=game.getBoxArtPath() %>" /></div>
        <h1>Description</h1>
        <div class="game-description"><%=game.getDescription() %></div>
      </div>
      <div class="w-col w-col-3 game-info-col-2">
        <div class="game-info"><strong>Genre:</strong> <%=game.getGenreString() %>
          <br>
          <br><strong>Release Date</strong>: <%=game.getReleaseDate() %>
          <br>
          <br><strong>Developer:</strong> <%=game.getDeveloper().getName() %>
          <br>
          <br><strong>Publisher:</strong> <%=game.getPublisher().getName() %>
          <br>
          <br><strong>ESRB Rating:</strong> <%=game.getESRBRating().toString() %>
		</div>
        <div class="w-form">
          <form id="email-form" name="email-form" data-name="Email Form">
            <input class="w-button add-to-cart" id="For-adding-to-cart" type="submit" value="ADD TO CART" data-wait="Please wait...">
          </form>
          <div class="w-form-done">
            <p>Thank you! Your submission has been received!</p>
          </div>
          <div class="w-form-fail">
            <p>Oops! Something went wrong while submitting the form :(</p>
          </div>
        </div>
      </div>
    </div>
    <h1 class="game-rating-text">Average Rating:</h1>
    <h1 class="text-rating">4.5</h1>
  </div>
  <!--
  <div class="w-section w-clearfix footer"><img class="footer-logo" src="images/game go logo.png" width="169" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
    <div class="footer-text">Copyright 2015. All rights reserved.&nbsp;All trademarks&nbsp;are property of their respective owners in the US and other countries</div>
  </div>
  -->
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script type="text/javascript" src="js/webflow.js"></script>
  <!--[if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]-->
</body>
</html>