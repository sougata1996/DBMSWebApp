<!-- Add evaluation page for teacher -->

<!DOCTYPE html>
<html>

<head>
	<title>Add Evaluation</title>

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
		<h3>Add Evaluation</h3>
		
		<form action="EvaluationControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="INSERT" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Evaluation Type</label></td>
						<td><select name="eval_type" id="eval_type">
    					<option value="Homework">Homework</option>
    					<option value="Project">Project</option>
    					<option value="Assignment">Assignment</option>
    					<option value="Mid Term">Mid Term</option>
    					<option value="Final Term">Final Term</option>
  						</select></td>
					</tr>
					
					<tr>
						<td><label>Evaluation Name:</label></td>
						<td><input type="text" name="eval_name" /></td>
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