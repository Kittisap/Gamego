<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.user.*" %>

<%
	User user = User.getSessionUser(request);
	Database db = new Database();
	
	if(user == null)
		response.sendRedirect("./index.jsp");
		
%>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Your History" />
</jsp:include>

  <div class="w-container history-container">
    <h2>Your History</h2>
    <%= db.getHistoryHTML(user.getID()) %>
  </div>

<jsp:include page="footer.jsp" />
