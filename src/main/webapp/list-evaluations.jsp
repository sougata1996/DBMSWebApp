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
		
		<input type="button" value="Add Evaluation" 
	   onclick="window.location.href='add-evaluation.jsp'; return false;"
	   class="add-student-button"/>
		
			<table>
			
				<tr>
					<th>Evaluation Type</th>
					<th>Evaluation Name</th>
					<th>Action</th>
					
				</tr>
				
				<c:forEach var="tempEval" items="${eval_list}">
				
					<c:url var="updateLink" value="TeacherControllerServlet">
						<c:param name="command" value="UPDATE" />
						<c:param name="teacherId" value="${tempTeacher.id}" />
					</c:url>
					
					<c:url var="deleteLink" value="TeacherControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="teacherId" value="${tempTeacher.id}" />
					</c:url>
					
					<tr>
						<td> ${tempEval.eval_name} </td>
						<td> ${tempEval.eval_type} </td>
						<td> 
							<a href="${updateLink}">Update</a>
							| 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this evaluation record?'))) return false">
							Delete</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>