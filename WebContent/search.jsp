<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.search.*" %>

<%
	String query = request.getParameter("q");
	String genreID = request.getParameter("genreID");
	String resultPage = request.getParameter("page");
	
	Database db = new Database();
	Search search = null;
	boolean hasError = true;
	
	try
	{
		search = db.performSearch(query, genreID, resultPage);
		
		if(search != null)
			hasError = false;
	}
	catch(Exception e) {}
	
	if(hasError)
	{
		response.sendRedirect("./index.jsp");
		
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
  <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
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
  <div class="w-container search-page-container">
    <h2>Search</h2>
      <form id="email-form" name="email-form" data-name="Email Form" method="get" action="search.jsp">
        <input class="w-input game-search" id="search-Game" type="text" 
        	placeholder="Search for a game..." name="q" 
        	value="<%=search.getQuery() %>" />
        
        <%=db.getGenreDropdownHTML(search.getGenreID()) %>
        
        <br /><input class="searchMargin pure-button pure-button-primary" type="submit" value="Search" />
      </form>
  </div>
  <div class="w-container search-page-container">
    <h2>Results</h2>
    <%=search.getResultsHTML() %>
    <%=search.getPageLinksHTML() %>
  </div>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <!-- <script type="text/javascript" src="js/webflow.js"></script> -->
  <!--[if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]-->
</body>
</html>