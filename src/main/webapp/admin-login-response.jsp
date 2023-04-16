<!-- Login response page for admin -->

<html>

<head>
	<title>Score Management App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
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
			<h2>Welcome Admin!</h2>
		</div>
	</div>
	
	<div class="d-flex flex-row">
	<div class="card" style="width: 18rem;">
  	<img src="images/teacher.png" class="card-img-top" height= "200" width= "300">
  		<div class="card-body">
    		<h5 class="card-title">Teacher Information</h5>
    		<p class="card-text">Add, delete, update and view teacher's information.</p>
    <a href="TeacherControllerServlet" class="btn btn-primary">Click here</a>
  </div>
  </div>
  
  <div class="card" style="width: 18rem;">
  	<img src="images/student.png" class="card-img-top" height= "200" width= "300">
  		<div class="card-body">
    		<h5 class="card-title">Student Information</h5>
    		<p class="card-text">Add, delete, update and view student's information.</p>
    <a href="StudentControllerServlet" class="btn btn-primary">Click here</a>
  </div>
  </div>
</div>

<a href="landingpage.jsp" class="link">Log Out</a>
</body>
</html>