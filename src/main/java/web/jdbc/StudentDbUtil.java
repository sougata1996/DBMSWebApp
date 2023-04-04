package web.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;

	Connection myConn = null;
	Statement myStmt = null;
	CallableStatement statement = null;
	ResultSet myRs = null;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "call viewStudentCourseData()";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				int courseId = myRs.getInt("course_id");
				String courseName = myRs.getNString("course_name");
				
				// create new student object
				Student temp = new Student(id, firstName, lastName, email, courseId, courseName);
				
				// add it to the list of students
				students.add(temp);				
			}
			
			return students;
		}
		finally {
			// close JDBC objects
			close();
		}
	}

	private void close() {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addStudent(Student theStudent) throws Exception {
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call insertStudentData(?,?,?,?)}");
			statement.setInt(1, theStudent.getId());
			statement.setString(2, theStudent.getEmail());
			statement.setString(3, theStudent.getFirstName());
			statement.setString(4, theStudent.getLastName());
			statement.execute();
			
			statement = myConn.prepareCall("{call insertStudentCourseData(?,?)}");
			statement.setInt(1, theStudent.getId());
			statement.setInt(2, theStudent.getCourseId());
			statement.execute();
			}
			finally {
				// close JDBC objects
				close();
			}
	}

	public void deleteStudent(int id) throws Exception {
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call deleteStudent(?)}");
			statement.setInt(1, id);
			statement.execute();
			}
			finally {
				close();
			}
	}

	public void updateStudent(Student theStudent) throws Exception {
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call updateStudent(?, ?, ?)}");
			statement.setInt(1, theStudent.getId());
			statement.setString(2, theStudent.getFirstName());
			statement.setString(3, theStudent.getLastName());
			statement.execute();
			}
			finally {
				close();
			}
	}

	public Student getStudent(String theStudentId) throws Exception {
		Student theStudent = null;
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call getAllStudentsData(?)}");
			statement.setInt(1, Integer.parseInt(theStudentId));
			myRs = statement.executeQuery();
			
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				
				// use the studentId during construction
				theStudent = new Student(id, firstName, lastName);
			}
			else {
				throw new Exception("Could not find student id: " + theStudent);
			}
		}
		finally {
			// close JDBC objects
			close();
		}
		
		return theStudent;
	}

}