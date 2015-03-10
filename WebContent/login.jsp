<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gamego.user.*" %>

<%
	if(User.isLoggedIn(request))
		response.sendRedirect("./index.jsp");
%>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Login" />
</jsp:include>

  <div class="w-container search-page-container">
    <h2>Login</h2>
    <div class="searchMargin">
      <form class="signup-container" id="email-form" method="post" action="./login">
        <label for="Username">Username</label>
        <input class="w-input" id="Username" type="text" placeholder="Please enter your username" name="username" required="required" />
        <label for="Password">Password</label>
        <input class="w-input" id="Password" type="password" placeholder="Please enter your password" name="password" required="required" />
        <input class="pure-button pure-button-primary" type="submit" value="Login" />
      </form>
	</div>
  </div>

<jsp:include page="footer.jsp" />
