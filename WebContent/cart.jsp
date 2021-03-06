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

<%
Database db = new Database();
String[] idList = new String[0];
Game[] gameList = new Game[0];
Game gameToAdd;
float totalPrice = 0;

try
{
	cart.addGame(request.getParameter("gameId"));
	idList = cart.getCart();
	gameList = new Game[idList.length];
	for (int i = 0; i < gameList.length; i++)
	{
		gameList[i] = db.selectGame(Integer.parseInt(idList[i]));
	}
}
catch(Exception e){}
%>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Cart" />
</jsp:include>

  <div class="w-container cart-container">
  	<h2>Your Cart</h2>
    <ul class="w-list-unstyled list-of-cart-items">
    <%
    try
    {
        for (int i = 0; i < gameList.length; i++)
        {
        	totalPrice += gameList[i].getPrice();
        	if(gameList[i].getBoxArtPath().length() > 0)
        	%>
        	<li class="w-clearfix">
        	<a href="./product?id=<%=gameList[i].getID()%>">
        		<img class="cart-item-image-example" src="<%=gameList[i].getBoxArtPath() %>">
			</a>
        	<div class="cart-item-name-example"><a href="./product?id=<%=gameList[i].getID()%>"><%=gameList[i].getTitle() %></a></div>
        	<div class="cart-item-price-example"><%=gameList[i].getFormattedPrice() %></div>
        	</li>
        	<%
        }
    }
    catch(Exception e) {}
    DecimalFormat df = new DecimalFormat("0.00");
    String priceString = df.format(totalPrice);
    
    %>
    	<li class="w-clearfix">
    		<div class="cart-item-name-example">TOTAL PRICE: $<%=priceString %></div>
    	</li>
    </ul>
    <div class="cart-item-name-example w-form">
      <form class="w-clearfix" id="email-form-2" name="email-form-2" data-name="Email Form 2" method="post" action="./checkout">
        <input class="pure-button pure-button-primary" type="submit" value="Purchase" />
        <input type="hidden" name="amount" value="<%=priceString %>" />
      </form>
    </div>
  </div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
