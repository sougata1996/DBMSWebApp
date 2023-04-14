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
				
				<c:forEach var="tempCourseId" items="${courseId_list}" varStatus="loop">
					
					<c:url var="viewEvaluation" value="StudentControllerServlet">
						<c:param name="command" value="STUDENT_COURSE_LIST" />
						<c:param name="teacherId" value="${teacherId}" />
						<c:param name="courseId" value="${tempCourseId}" />
					</c:url>
					
					<tr>
						<td> ${tempCourseId} </td>
						<td> ${courseName_list[loop.index]} </td>
						<td>
							<a href="${viewEvaluation}">View Students</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>