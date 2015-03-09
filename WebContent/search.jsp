<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.search.*" %>

<%
	String query = request.getParameter("q");
	String resultPage = request.getParameter("page");
	
	Search search = null;
	boolean hasError = true;
	
	if(query != null)
	{
		Database db = new Database();
		
		try
		{
			if(resultPage == null)
				search = db.performSearch(query);
			else
				search = db.performSearch(query, Integer.parseInt(resultPage));
			
			if(search != null)
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
<html data-wf-site="54eb722519500e4473ec8320" data-wf-page="54ece1e6fccfc9e421e2cc3b">
<head>
  <meta charset="utf-8">
  <title>Search Games - Game Go!</title>
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
  <div class="w-container genre-selector-box">
    <div class="w-form genre-form-wrapper">
      <form id="email-form" name="email-form" data-name="Email Form" method="get" action="search.jsp">
        <div class="w-row">
          <div class="w-col w-col-3">
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input action-checkbox" id="checkbox" type="checkbox" name="checkbox" data-name="Checkbox">
              <label class="w-form-label" for="checkbox">Action</label>
            </div>
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input simulation-checkbox" id="checkbox-2" type="checkbox" name="checkbox-2" data-name="Checkbox 2">
              <label class="w-form-label" for="checkbox-2">Simulation</label>
            </div>
          </div>
          <div class="w-col w-col-3">
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input adventure-checkbox" id="checkbox-3" type="checkbox" name="checkbox-3" data-name="Checkbox 3">
              <label class="w-form-label" for="checkbox-3">Adventure</label>
            </div>
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input strategy-checkbox" id="checkbox-4" type="checkbox" name="checkbox-4" data-name="Checkbox 4">
              <label class="w-form-label" for="checkbox-4">Strategy</label>
            </div>
          </div>
          <div class="w-col w-col-3">
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input shooter-checkbox" id="checkbox-5" type="checkbox" name="checkbox-5" data-name="Checkbox 5">
              <label class="w-form-label" for="checkbox-5">Shooter</label>
            </div>
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input sports-checkbox" id="checkbox-6" type="checkbox" name="checkbox-6" data-name="Checkbox 6">
              <label class="w-form-label" for="checkbox-6">Sports</label>
            </div>
          </div>
          <div class="w-col w-col-3">
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input rp-checkbox" id="checkbox-7" type="checkbox" name="checkbox-7" data-name="Checkbox 7">
              <label class="w-form-label" for="checkbox-7">Role Playing</label>
            </div>
            <div class="w-checkbox w-clearfix">
              <input class="w-checkbox-input other-checkbox" id="checkbox-8" type="checkbox" name="checkbox-8" data-name="Checkbox 8">
              <label class="w-form-label" for="checkbox-8">Other</label>
            </div>
          </div>
        </div>
        <input class="w-input game-search" id="search-Game" type="text" placeholder="Search for a Title, Developer, Publisher, etc.." name="q" required="required" data-name="search Game" value="<%=search.getQuery() %>" />
      </form>
      <div class="w-form-done">
        <p>Thank you! Your submission has been received!</p>
      </div>
      <div class="w-form-fail">
        <p>Oops! Something went wrong while submitting the form :(</p>
      </div>
    </div>
  </div>
  <div class="w-container search-page-container">
    <h2>Results</h2>
    <%=search.getResultHTML() %>
    <!--
    <ul>
      <li class="w-clearfix">
        <a class="w-clearfix w-inline-block" href="#"><img class="game-search-image" src="https://d3e54v103j8qbb.cloudfront.net/img/image-placeholder.svg" alt="image-placeholder.svg">
        </a>
        <div class="game-search-title">This is the name of the game</div>
        <div class="game-search-description">This is the description</div>
      </li>
      <li></li>
      <li></li>
	</ul>
	-->
  </div>
  <div class="w-section w-clearfix footer"><img class="footer-logo" src="images/game go logo.png" width="169" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
    <div class="footer-text">Copyright 2015. All rights reserved.&nbsp;All trademarks&nbsp;are property of their respective owners in the US and other countries</div>
  </div>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <!-- <script type="text/javascript" src="js/webflow.js"></script> -->
  <!--[if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]-->
</body>
</html>