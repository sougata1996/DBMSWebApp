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
		
		<input type="button" value="Add Teacher" 
	   onclick="window.location.href='add-teacher-form.jsp'; return false;"
	   class="add-student-button"/>
		
			<table>
			
				<tr>
					<th>Teacher Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Course Id</th>
					<th>Course Name</th>
					<th>Action</th>
					
				</tr>
				
				<c:forEach var="tempTeacher" items="${TEACHER_LIST}">
				
					<c:url var="tempLink" value="TeacherControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="teacherId" value="${tempTeacher.id}" />
					</c:url>
					
					<c:url var="deleteLink" value="TeacherControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="teacherId" value="${tempTeacher.id}" />
					</c:url>
					
					<tr>
						<td> ${tempTeacher.id} </td>
						<td> ${tempTeacher.firstName} </td>
						<td> ${tempTeacher.lastName} </td>
						<td> ${tempTeacher.email} </td>
						<td> ${tempTeacher.courseId} </td>
						<td> ${tempTeacher.courseName} </td>
						<td> 
							<a href="${tempLink}"> Update</a>
							| 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this teacher?'))) return false">
							Delete</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>