<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Register" />
</jsp:include>

  <div class="w-container signup-form login-form">
    <div class="w-form signup-form-wrapper">
      <form class="signup-container" id="email-form" name="email-form" data-name="Email Form">
        <label class="username-field" for="Username-3">Username</label>
        <input class="w-input username-text-field" id="Username-3" type="text" placeholder="Please enter your username" name="Username" data-name="Username" required="required" autofocus="autofocus">
        <label class="email-field email-field" for="Email">Email</label>
        <input class="w-input email-text-field" id="Email" type="email" placeholder="Please enter your email" name="Email" data-name="Email" required="required">
        <label class="password-field" for="Password">Password</label>
        <input class="w-input password-text-field" id="Password" type="password" placeholder="Please enter your password" name="Password" data-name="Password" required="required">
        <label class="password-field" for="Password-2">Password Confirmation</label>
        <input class="w-input password-text-field" id="Password-2" type="password" placeholder="Please enter your password again" name="Password-2" data-name="Password 2" required="required">
        <input class="w-button login-button" type="submit" value="Submit" data-wait="Please wait...">
      </form>
    </div>
  </div>

<jsp:include page="footer.jsp" />
