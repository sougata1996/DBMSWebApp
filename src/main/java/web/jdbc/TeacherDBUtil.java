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

public class TeacherDBUtil {

	private DataSource dataSource;

	Connection myConn = null;
	Statement myStmt = null;
	CallableStatement statement = null;
	ResultSet myRs = null;
	
	public TeacherDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Teacher> getTeachers() throws Exception {
		
		List<Teacher> teachers = new ArrayList<>();
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "call viewTeacherCourseData()";
			
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
				Teacher tempTeacher = new Teacher(id, firstName, lastName, email, courseId, courseName);
				
				// add it to the list of students
				teachers.add(tempTeacher);				
			}
			
			return teachers;
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

	public void addTeacher(Teacher theTeacher) throws Exception {
		try {
		myConn = dataSource.getConnection();
		statement = myConn.prepareCall("{call insertTeacherData(?,?,?,?)}");
		statement.setInt(1, theTeacher.getId());
		statement.setString(2, theTeacher.getEmail());
		statement.setString(3, theTeacher.getFirstName());
		statement.setString(4, theTeacher.getLastName());
		statement.execute();
		
		statement = myConn.prepareCall("{call insertTeacherCourseData(?,?,?)}");
		statement.setInt(1, theTeacher.getCourseId());
		statement.setString(2, theTeacher.getCourseName());
		statement.setInt(3, theTeacher.getId());
		statement.execute();
		}
		finally {
			// close JDBC objects
			close();
		}
	}

	public List<Teacher> getTeacherCourses(String theTeacherId) throws Exception {
		List<Teacher> teachers = new ArrayList<>();
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call getAllTeachersData(?)}");
			statement.setInt(1, Integer.parseInt(theTeacherId));
			myRs = statement.executeQuery();
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				int courseId = myRs.getInt("course_id");
				String courseName = myRs.getString("course_name");
				// use the studentId during construction
				Teacher record = new Teacher(id, firstName, lastName, email, courseId, courseName);
				teachers.add(record);
			}
		}
		finally {
			// close JDBC objects
			close();
		}
		
		return teachers;
	}
	
	public List<Teacher> getTeacher(String theTeacherId) throws Exception {
		List<Teacher> teachers = new ArrayList<>();
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call getAllTeachersData(?)}");
			statement.setInt(1, Integer.parseInt(theTeacherId));
			myRs = statement.executeQuery();
			if(myRs.next()) {
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				int courseId = myRs.getInt("course_id");
				String courseName = myRs.getString("course_name");
				// use the studentId during construction
				Teacher record = new Teacher(id, firstName, lastName, email, courseId, courseName);
				teachers.add(record);
			}
			}
			else {
				throw new Exception("Could not find teacher id: " + theTeacherId);
			}
		}
		finally {
			// close JDBC objects
			close();
		}
		
		return teachers;
	}

	public void updateTeacher(Teacher theTeacher) throws Exception {
		try {
		myConn = dataSource.getConnection();
		statement = myConn.prepareCall("{call updateTeacher(?, ?, ?, ?)}");
		statement.setInt(1, theTeacher.getId());
		statement.setString(2, theTeacher.getFirstName());
		statement.setString(3, theTeacher.getLastName());
		statement.setString(4, theTeacher.getCourseName());
		statement.execute();
		}
		finally {
			close();
		}
	}

	public void deleteTeacher(int id) throws Exception {
		try {
		myConn = dataSource.getConnection();
		statement = myConn.prepareCall("{call deleteTeacher(?)}");
		statement.setInt(1, id);
		statement.execute();
		}
		finally {
			close();
		}
	}
}