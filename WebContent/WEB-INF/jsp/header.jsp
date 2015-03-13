<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gamego.user.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>GameGo!<c:if test="${not empty param.title}"> - ${param.title}</c:if></title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="generator" content="Webflow">
  <link rel="stylesheet" type="text/css" href="css/normalize.css">
  <link rel="stylesheet" type="text/css" href="css/webflow.css">
  <link rel="stylesheet" type="text/css" href="css/game-go.webflow.css">
  <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.4.7/webfont.js"></script>
  <script>
    WebFont.load({
      google: {
        families: ["Exo:100,100italic,200,200italic,300,300italic,400,400italic,500,500italic,600,600italic,700,700italic,800,800italic,900,900italic","Bitter:400,700,400italic","Ubuntu:300,300italic,400,400italic,500,500italic,700,700italic","Lato:100,100italic,300,300italic,400,400italic,700,700italic,900,900italic","Droid Sans:400,700"]
      }
    });
  </script>
  <script type="text/javascript" src="js/modernizr.js"></script>
</head>
<body>
  <div class="w-nav navbar main-navbar" data-collapse="medium" data-animation="default" data-duration="400" data-contain="1">
    <div class="w-container">
      <a class="w-nav-brand brand-block" href="./index.jsp">
      	<img class="gamego-image" src="images/game go logo.png" width="214" height="62" alt="54eb77b357cf2eaa083890a1_game%20go%20logo.png">
      </a>
      <nav class="w-nav-menu" role="navigation">
      	<%
      		if(!User.isLoggedIn(request))
      		{
      	%>
      	<a class="w-nav-link nav-link-login" href="./login"><strong>Login</strong></a>
      	<a class="w-nav-link nav-link-signup" href="./register">Register</a>
      	<%
      		}
      		else
      		{
      			User user = User.getSessionUser(request);
      	%>
      	<a class="w-nav-link nav-link-signup" href="#"><%=user.getUsername() %></a>
      	<%
      			if(user.isAdmin())
      			{
      	%>
      	<a class="w-nav-link nav-link-signup" href="./admin">Admin</a>
      	<%
      			}
      	%>
      	<a class="w-nav-link nav-link-signup" href="./history">History</a>
      	<a class="w-nav-link nav-link-signup" href="./logout">Logout</a>
      	<%
      		}
      	%>
      	<a class="w-nav-link nav-link-cart" href="./cart.jsp">Cart</a>
      </nav>
      <div class="w-nav-button">
        <div class="w-icon-nav-menu"></div>
      </div>
    </div>
  </div>
