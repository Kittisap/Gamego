<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gamego.user.*" %>

<%
	if(User.isLoggedIn(request))
		response.sendRedirect("./index.jsp");
%>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Register" />
</jsp:include>

  <div class="w-container search-page-container">
    <h2>Register</h2>
    <div class="searchMargin">
      <form class="signup-container" id="email-form" method="post" action"./register">
        <label for="username">Username</label>
        <input class="w-input" id="username" type="text" placeholder="Please enter your username" name="username" required="required" autofocus="autofocus" />
        <label for="email">Email</label>
        <input class="w-input" id="email" type="email" placeholder="Please enter your email" name="email" required="required" />
        <label for="password">Password</label>
        <input class="w-input" id="Password" type="password" placeholder="Please enter your password" name="password" required="required" />
        <label for="passwordConfirm">Password Confirmation</label>
        <input class="w-input" id="passwordConfirm" type="password" placeholder="Please enter your password again" name="passwordConfirm" required="required" />
        <input class="pure-button pure-button-primary" type="submit" value="Register" />
      </form>
	</div>
  </div>

<jsp:include page="footer.jsp" />
