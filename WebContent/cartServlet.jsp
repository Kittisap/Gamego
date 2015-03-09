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
if (user.isInitialized())
{
	Database db = new Database();
	db.addCart(cart.getCart(), 1);
	cart.clear();
	%>
	<script>window.location.href = "cart.jsp"</script>
	<%
}
else
{
	%>
	<script>window.location.href = "login.html"</script>
	<%
}

%>