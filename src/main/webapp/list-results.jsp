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
		
		<input type="button" value="Add Result" 
	   onclick="window.location.href='add-result.jsp'; return false;"
	   class="add-student-button"/>
		
			<table>
			
				<tr>
					<th>Evaluation Type</th>
					<th>Evaluation Name</th>
					<th>Score</th>
					<th>Action</th>
					
				</tr>
				
				<c:forEach var="tempResult" items="${result_list}">
				
					<c:url var="updateLink" value="ResultControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="student_id" value="${tempResult.student_id}" />
						<c:param name="course_id" value="${tempResult.course_id}" />
						<c:param name="eval_type" value="${tempResult.eval_type}" />
						<c:param name="eval_name" value="${tempResult.eval_name}" />
						<c:param name="score" value="${tempResult.score}" />
					</c:url>
					<c:url var="deleteLink" value="ResultControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="student_id" value="${tempResult.student_id}" />
						<c:param name="course_id" value="${tempResult.course_id}" />
						<c:param name="eval_type" value="${tempResult.eval_type}" />
						<c:param name="eval_name" value="${tempResult.eval_name}" />
						<c:param name="score" value="${tempResult.score}" />
					</c:url>
					
					<tr>
					
						<td> ${tempResult.eval_type} </td>
						<td> ${tempResult.eval_name} </td>
						<td> ${tempResult.score} </td>
						<td> 
							<a href="${updateLink}">Update</a>
							<a href="${deleteLink}">Delete</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>