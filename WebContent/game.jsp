<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.gamego.db.*" %>
<%@ page import="com.gamego.game.*" %>
<%
	Game game = null;
	boolean hasError = true;

	String gameID = request.getParameter("id");
	
	if(gameID != null)
	{
		Database db = new Database();

		try
		{
			game = db.selectGame(Integer.parseInt(gameID));
			
			if(game != null)
				hasError = false;
		}
		catch(Exception e) {}
	}
	
	if(hasError)
	{
		response.sendRedirect("./index.jsp");
		
		return;
	}
%>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="<%=game.getTitle() %>" />
</jsp:include>

  <div class="w-container game-container">
    <div class="w-row game-row-1">
      <div class="w-col w-col-8 game-info-col-1">
        <h2><%=game.getTitle() %></h2><br />
        <div class="game-image-container"><img class="game-image" src="<%=game.getBoxArtPath() %>" /></div>
        <h2>Description</h2>
        <div class="game-description"><%=game.getDescription() %></div>
      </div>
      <div class="w-col w-col-4 game-info-col-2">
        <div class="game-info">
			<strong>Genre:</strong> <%=game.getGenreString() %><br /><br />
			<strong>Release Date</strong>: <%=game.getReleaseDate() %><br /><br />
			<strong>Developer:</strong> <%=game.getDeveloper().getName() %><br /><br />
			<strong>Publisher:</strong> <%=game.getPublisher().getName() %><br /><br />
			<strong>ESRB Rating:</strong> <%=game.getESRBRating().getName() %><br /><br />
			<strong>Average User Rating:</strong> <%=game.getUserRating() %><br /><br />
			<strong>Your Rating:</strong>
			
			<select name="rating">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
			<input type="button" class="pure-button pure-button-primary" value="Rate" />
		</div>
        <div class="w-form">
          <form id="email-form" method="post" action="./cart.jsp">
            <input class="pure-button pure-button-primary add-to-cart" type="submit" value="Add to Cart" />
            <input type="hidden" name="gameId" value="<%=game.getID() %>" />
          </form>
        </div>
      </div>
    </div>
  </div>

<jsp:include page="footer.jsp" />
