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