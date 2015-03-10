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
      <div class="w-col w-col-9 game-info-col-1">
        <h1><%=game.getTitle() %></h1><div class="game-image-container"><img class="game-image" src="<%=game.getBoxArtPath() %>" /></div>
        <h1>Description</h1>
        <div class="game-description"><%=game.getDescription() %></div>
      </div>
      <div class="w-col w-col-3 game-info-col-2">
        <div class="game-info"><strong>Genre:</strong> <%=game.getGenreString() %>
          <br>
          <br><strong>Release Date</strong>: <%=game.getReleaseDate() %>
          <br>
          <br><strong>Developer:</strong> <%=game.getDeveloper().getName() %>
          <br>
          <br><strong>Publisher:</strong> <%=game.getPublisher().getName() %>
          <br>
          <br><strong>ESRB Rating:</strong> <%=game.getESRBRating().getName() %>
		</div>
        <div class="w-form">
          <form id="email-form" name="email-form" data-name="Email Form" type=POST action=cart.jsp>
            <input class="w-button add-to-cart" id="For-adding-to-cart" type=submit value="ADD TO CART">
            <input type="hidden" name="gameId" value="<%=game.getID() %>" />
          </form>
        </div>
      </div>
    </div>
    <h1 class="game-rating-text">Average Rating:</h1>
    <h1 class="text-rating">4.5</h1>
  </div>

<jsp:include page="footer.jsp" />
