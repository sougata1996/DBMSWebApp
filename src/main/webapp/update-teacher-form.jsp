<!-- Update teacher form -->

<!DOCTYPE html>
<html>

<head>
	<title>Update Teacher</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Northeastern University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Teacher</h3>
		
		<form action="TeacherControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="teacherId" value="${THE_TEACHER.id}" />
			<input type="hidden" name="courseIds" value="${THE_TEACHER.courseIds}" />
			
			<table>
				<tbody>
					
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" value="${THE_TEACHER.firstName}"/></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" value="${THE_TEACHER.lastName}"/></td>
					</tr>
					
					<tr>
  						<td><label>Course Names:</label></td>
  						<td><input type="text" name="courseNames" value="${THE_TEACHER.courseNames.toString().replace('[','').replace(']','')}"/></td>
					</tr>
					
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="TeacherControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>