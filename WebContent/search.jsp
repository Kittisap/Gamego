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

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="<%=search.getQuery() %>" />
</jsp:include>

  <div class="w-container search-page-container">
    <h2>Search</h2>
      <form id="email-form" method="get" action="search.jsp">
        <input class="w-input game-search" id="search-Game" type="text" 
        	placeholder="Search for a game..." name="q" 
        	value="<%=search.getQuery() %>" />
        
        <%=db.getGenreDropdownHTML(search.getGenreID()) %>
        
        <br />
        <input class="searchMargin pure-button pure-button-primary" type="submit" value="Search" />
      </form>
  </div>
  <div class="w-container search-page-container">
    <h2>Results</h2>
    <%=search.getResultsHTML() %>
    <%=search.getPageLinksHTML() %>
  </div>

<jsp:include page="footer.jsp" />
