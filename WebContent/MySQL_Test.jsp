<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="data.dbConnect.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MySQL Test</title>
</head>
<body>
<%
	String driver = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://localhost:3306/css490d";
	String dbUsername = "root";
	String dbPassword = "root";
	
	DBConnectionPool connPool = new DBConnectionPool(url, dbUsername, dbPassword);
	Connection conn = connPool.getConnection();
	
	out.print(conn == null ? "Failure" : "Success");
%>
</body>
</html>
