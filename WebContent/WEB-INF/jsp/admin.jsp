<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Admin" />
</jsp:include>

<div class="w-container stats-container">
	<h2>Admin</h2>
	<div class="searchMargin">
	    <h1>Sales Statistics</h1>
	    <div class="w-row">
	    	<div class="w-col w-col-3">
	    		<div class="stat-category">
		        	<strong>Total Profit:</strong><br /><br />
		        	<strong>Current Week Profit:</strong><br />
		        	<strong>Current Month Profit:</strong><br />
		        	<strong>Current Annual Profit:</strong><br /><br>
		        	<strong>Previous Week Profit:</strong><br />
		        	<strong>Previous Month Profit:</strong><br />
		        	<strong>Previous Annual Profit:</strong>
				</div>
			</div>
			<div class="w-col w-col-4">
				<div class="numbers-block">
		        	<span>${totalProfit}</span><br /><br />
		        	<span>${currentWeekProfit} (${weekDifference} since last week)</span><br />
		        	<span>${currentMonthProfit} (${monthDifference} since last month)</span><br />
		        	<span>${currentAnnualProfit} (${annualDifference} since last year)</span><br /><br />
		        	<span>${previousWeekProfit}</span><br />
		        	<span>${previousMonthProfit}</span><br />
		        	<span>${previousAnnualProfit}</span>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
