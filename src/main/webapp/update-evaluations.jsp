<!DOCTYPE html>
<html>

<head>
	<title>Update Student</title>

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
		<h3>Update Evaluation</h3>
		
		<form action="EvaluationControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="eval_type" value="${evaluation.eval_type}" />
			<input type="hidden" name="old_eval_name" value="${evaluation.eval_name}" />
			
			<table>
				<tbody>
					
					<tr>
						<td><label>Evaluation Type:</label></td>
						<td>${evaluation.eval_type}</td>
					</tr>

					<tr>
						<td><label>Evaluation name:</label></td>
						<td><input type="text" name="new_eval_name" value="${evaluation.eval_name}"/></td>
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
			<a href="EvaluationControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>