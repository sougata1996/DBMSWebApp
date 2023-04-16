<!-- View list of student courses -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
					<th>Course Id</th>
					<th>Course Name</th>
				</tr>
				
				<c:forEach var="i" begin="0" end="${fn:length(STUDENT_COURSE_LIST.courseIds) - 1}">
					<tr>
  						<td>${STUDENT_COURSE_LIST.courseIds[i]}</td>
    					<td>${STUDENT_COURSE_LIST.courseNames[i]}</td>
					</tr>
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>