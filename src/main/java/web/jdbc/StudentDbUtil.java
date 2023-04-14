package web.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<Integer, List<Integer>> multiMap_1 = new HashMap<>();
		List<Integer> courseIds = new ArrayList<>();
		
		Map<Integer, List<String>> multiMap_2 = new HashMap<>();
		List<String> courseNames = new ArrayList<>();
		
		Map<Integer, Student> multiMap_3 = new HashMap<>();
		
		List<Student> res = new ArrayList<>();
		
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
				
				if (multiMap_1.containsKey(id) && multiMap_1.isEmpty() == false) {
					courseIds.add(courseId);
				}
				else {
					courseIds = new ArrayList<>();
					courseIds.add(courseId);
				}
				
				if (multiMap_2.containsKey(id) && multiMap_2.isEmpty() == false) {
					courseNames.add(courseName);
				}
				else {
					courseNames = new ArrayList<>();
					courseNames.add(courseName);
				}
				
				multiMap_1.put(id, courseIds);
				multiMap_2.put(id, courseNames);
				// create new student object
				Student tempStudent = new Student(id, firstName, lastName, email, multiMap_1.get(id), multiMap_2.get(id));
				
				// add it to the list of students
				multiMap_3.put(id, tempStudent);
			}
			
			for (Map.Entry<Integer, Student> entry : multiMap_3.entrySet()) {
			    res.add(entry.getValue());
			}
			
			return res;
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
			statement = myConn.prepareCall("{call getAllStudentsData(?)}");
			statement.setInt(1, theStudent.getId());
			myRs = statement.executeQuery();
			
			if (!myRs.next()) {
				statement = myConn.prepareCall("{call insertStudentData(?,?,?,?)}");
				statement.setInt(1, theStudent.getId());
				statement.setString(2, theStudent.getEmail());
				statement.setString(3, theStudent.getFirstName());
				statement.setString(4, theStudent.getLastName());
				statement.execute();
			}
			
			List<Integer> courses = theStudent.getCourseIds();
			for (int i=0; i<courses.size(); i++)
			{
				statement = myConn.prepareCall("{call insertStudentCourseData(?,?)}");
				statement.setInt(1, theStudent.getId());
				statement.setInt(2, theStudent.getCourseId(i));
				statement.execute();
			}
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
	
	public Student getStudentCourses(String theStudentId) throws Exception {

		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call getAllStudentsData(?)}");
			statement.setInt(1, Integer.parseInt(theStudentId));
			myRs = statement.executeQuery();
			int id, courseId;
			id = courseId = 0;
			String firstName, lastName, email ;
			firstName = lastName = email = "";
			List<Integer> courseIds = new ArrayList<>();
			List<String> courseNames = new ArrayList<>();
			while (myRs.next()) {
				 id = myRs.getInt("id");
				 firstName = myRs.getString("first_name");
				 lastName = myRs.getString("last_name");
				 email = myRs.getString("email");
				 courseId = myRs.getInt("course_id");
				String courseName = myRs.getString("course_name");
				courseIds.add(courseId);
				courseNames.add(courseName);
			}

			return new Student(id, firstName, lastName,email,courseIds, courseNames);
			
		}
		finally {
			// close JDBC objects
			close();
		}
	}
	public List<Student> getStudentsInACourse(int courseId) throws Exception {
		List<Student> studentList = new ArrayList<>();
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call studentsInACourse(?)}");
			statement.setInt(1, courseId);
			myRs = statement.executeQuery();
			while (myRs.next()) {
				int id = myRs.getInt("id");
				 String firstName = myRs.getString("first_name");
				 String lastName = myRs.getString("last_name");
				 String email = myRs.getString("email");
				Student student = new Student(id, firstName,
						lastName, email);
				studentList.add(student);
			}
	}
		finally {
			// close JDBC objects
			close();
		}
		return studentList;
	}
}