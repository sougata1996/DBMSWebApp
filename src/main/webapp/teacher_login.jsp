<!-- Teacher login page -->

<!DOCTYPE html>
<html>
  <head>
    <title>Teacher Login Page</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  </head>
  <body>
    <div class="container mt-5">
      <div class="row">
        <div class="col-md-6 mx-auto">
          <div class="card">
            <div class="card-body">
              <h3 class="card-title text-center mb-4">Teacher Login Page</h3>
              <form action="TeacherLoginServlet">
                <div class="form-group">
                  <label for="username">Teacher ID</label>
                  <input type="text" class="form-control" name="username" id="username" placeholder="Enter Teacher Id">
                </div>
                <div class="form-group">
                  <label for="password">Password</label>
                  <input type="password" class="form-control" name="password" id="password" placeholder="Enter Password">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Login</button>
                <a href="signup-response.jsp" class="btn btn-link btn-block">Signup</a>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  </body>
</html>