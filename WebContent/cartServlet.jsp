<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>
<%@ page import="com.gamego.cart.*" %>
<%@ page import="com.gamego.user.*" %>
<%@ page import="java.text.DecimalFormat" %>

<jsp:useBean id="cart" class="com.gamego.cart.CartBean" scope="session" />

<%
if (User.isLoggedIn(request))
{
	Database db = new Database();
	User user = User.getSessionUser(request);
	
	if(db != null && user != null)
	{
		db.addCart(cart.getCart(), user.getID());
		cart.clear();
	}

	%>
	<script>window.location.href = "checkout.jsp"</script>
	<%
}
else
{
	%>
	<script>window.location.href = "./login"</script>
	<%
}

%>
