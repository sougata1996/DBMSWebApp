<!-- Used by student to view results for a particular course -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<title>Score Management App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Northeastern University</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		<form id="form" action="ResultControllerServlet" method="GET">
  				<select id="eval_type" name="Evaluation Type" onchange="redirectToServlet(this.value)">
   					<option value="Homework" <% if ("Homework".equals(request.getParameter("eval_type"))) { out.print("selected"); } %>>Homework</option>
    				<option value="Project" <% if ("Project".equals(request.getParameter("eval_type"))) { out.print("selected"); } %>>Project</option>
    				<option value="Assignment" <% if ("Assignment".equals(request.getParameter("eval_type"))) { out.print("selected"); } %>>Assignment</option>
    				<option value="Mid Term" <% if ("Mid Term".equals(request.getParameter("eval_type"))) { out.print("selected"); } %>>Mid Term</option>
    				<option value="Final Term" <% if ("Final Term".equals(request.getParameter("eval_type"))) { out.print("selected"); } %>>Final Term</option>
  				</select>
		</form>
		<script>
  			function redirectToServlet(selectedValue) {
    		var servletUrl = "ResultControllerServlet";
    		var queryParam = "?eval_type=" + encodeURIComponent(selectedValue) + "&command=VIEW";
    		var fullUrl = servletUrl + queryParam;
    		window.location.href = fullUrl;
  			}
		</script>
		<form action="ResultControllerServlet" method="GET">
		<button type="submit">Clear</button>
				   <input type="hidden" name="command" value=VIEW />
		</form>
			<div class="table-container">
				<table>
				
					<tr>
						<th>Evaluation Type</th>
						<th>Evaluation Name</th>
						<th>Score</th>
						
					</tr>
					
					<c:forEach var="tempResult" items="${result_list}">
						
						<tr>
						
							<td> ${tempResult.eval_type} </td>
							<td> ${tempResult.eval_name} </td>
							<td> ${tempResult.score}/100 </td>
						</tr>
					
					</c:forEach>
					
					<tr>
						<td colspan="2"></td> <!-- two empty cells to align with score column -->
						<td><strong>Average Score: ${average}</strong></td> <!-- average score cell -->
						<td></td> <!-- empty cell for the action column -->
					</tr>
					
				</table>
				
			</div>
		
		</div>
	
	</div>
</body>

</html>