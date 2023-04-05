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
			<table>
			
				<tr>
					<th>Course ID</th>
					<th>Course Name</th>
					<th>Action</th>
					
				</tr>
				
				<c:forEach var="tempCourse" items="${courses_list}">
				
					<c:url var="addEvaluation" value="EvaluationControllerServlet">
						<c:param name="command" value="ADD" />
						<c:param name="teacherId" value="${tempCourse.id}" />
						<c:param name="courseId" value="${tempCourse.courseId}" />
					</c:url>
					
					<c:url var="viewEvaluation" value="EvaluationControllerServlet">
						<c:param name="command" value="LIST" />
						<c:param name="teacherId" value="${tempCourse.id}" />
						<c:param name="courseId" value="${tempCourse.courseId}" />
					</c:url>
					
					<tr>
						<td> ${tempCourse.courseId} </td>
						<td> ${tempCourse.courseName} </td>
						<td> 
							<a href="${addEvaluation}"> Add</a>
							<a href="${viewEvaluation}"> View</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>