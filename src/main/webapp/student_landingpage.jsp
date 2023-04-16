<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<title>Score Management App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<style>
	/* Style for the link */
	.link {
	  position: absolute;
	  top: 0;
	  right: 0;
	  padding: 10px;
	  text-decoration: none;
	  color: #ffffff;
	  background-color: #000000;
	}
	</style>
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Welcome Student!</h2>
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
					
					<c:url var="viewResult" value="ResultControllerServlet">
						<c:param name="command" value="VIEW" />
						<c:param name="courseId" value="${tempCourseId}" />
					</c:url>
					
					<tr>
						<td> ${tempCourseId} </td>
						<td> ${courseName_list[loop.index]} </td>
						<td> 
							<a href="${viewResult}">View Results</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
	
<a href="landingpage.jsp" class="link">Log Out</a>
</body>


</html>