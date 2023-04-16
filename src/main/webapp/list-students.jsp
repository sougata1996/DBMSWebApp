<!-- View list of students added by admin -->

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
				
				<input type="button" value="Add Student" 
				   onclick="window.location.href='add-student-form.jsp'; return false;"
				   class="add-student-button"/>
				   
				   <input type="button" value="Back To Home" 
	   					onclick="window.location.href='admin-login-response.jsp'; return false;"
	   					class="add-student-button"/>
			
			<table>
			
				<tr>
					<th>Student Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${tempStudent.id}" />
					</c:url>
					
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${tempStudent.id}" />
					</c:url>
					
					<c:url var="viewCourseLink" value="StudentControllerServlet">
						<c:param name="command" value="VIEW_COURSE" />
						<c:param name="studentId" value="${tempStudent.id}" />
					</c:url>
					
					<tr>
						<td> ${tempStudent.id} </td>
						<td> ${tempStudent.firstName} </td>
						<td> ${tempStudent.lastName} </td>
						<td> ${tempStudent.email} </td>
						<td> 
							<a href="${tempLink}"> Update</a>
							| 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">
							Delete</a>
							|
							<a href="${viewCourseLink}"> View Course </a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








