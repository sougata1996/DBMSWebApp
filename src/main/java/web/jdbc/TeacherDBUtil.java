package web.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Model class that interacts with the teacher database.
 */
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
		
		Map<Integer, List<Integer>> multiMap_1 = new HashMap<>();
		List<Integer> courseIds = new ArrayList<>();
		
		Map<Integer, List<String>> multiMap_2 = new HashMap<>();
		List<String> courseNames = new ArrayList<>();
		
		Map<Integer, Teacher> multiMap_3 = new HashMap<>();
		
		List<Teacher> res = new ArrayList<>();
		
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
				// create new teacher object
				Teacher tempTeacher = new Teacher(id, firstName, lastName, email, multiMap_1.get(id), multiMap_2.get(id));
				
				// add it to the list of teachers
				multiMap_3.put(id, tempTeacher);
			}
			
			for (Map.Entry<Integer, Teacher> entry : multiMap_3.entrySet()) {
			    res.add(entry.getValue());
			}
			
			return res;
			
			//return teachers;
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
		statement = myConn.prepareCall("{call getAllTeachersData(?)}");
		statement.setInt(1, theTeacher.getId());
		myRs = statement.executeQuery();
		
		if (!myRs.next()) {
			statement = myConn.prepareCall("{call insertTeacherData(?,?,?,?)}");
			statement.setInt(1, theTeacher.getId());
			statement.setString(2, theTeacher.getEmail());
			statement.setString(3, theTeacher.getFirstName());
			statement.setString(4, theTeacher.getLastName());
			statement.execute();
		}
		
		List<Integer> courses = theTeacher.getCourseIds();
		for (int i=0; i<courses.size(); i++)
		{
			statement = myConn.prepareCall("{call insertTeacherCourseData(?,?,?)}");
			statement.setInt(1, theTeacher.getCourseId(i));
			statement.setString(2, theTeacher.getCourseName(i));
			statement.setInt(3, theTeacher.getId());
			statement.execute();
		}
		}
		finally {
			// close JDBC objects
			close();
		}
	}

	public Teacher getTeacher(int theTeacherId) throws Exception {
		Teacher theTeacher = null;
		List<Integer> courseIds = new ArrayList<Integer>();
		List<String> courseNames = new ArrayList<String>();
		int id = 0;
		String firstName = null, lastName = null, email = null;
		
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call getAllTeachersData(?)}");
			statement.setInt(1, theTeacherId);
			myRs = statement.executeQuery();
			
			while (myRs.next()) {
					id = myRs.getInt("id");
					firstName = myRs.getString("first_name");
					lastName = myRs.getString("last_name");
					email = myRs.getString("email");
					int courseId = myRs.getInt("course_id");
					String courseName = myRs.getString("course_name");
					courseIds.add(courseId);
					courseNames.add(courseName);
				}
				if(id == 0) {
					return null;
				}
				theTeacher = new Teacher(id, firstName, lastName, email, courseIds, courseNames);
			}
		finally {
			// close JDBC objects
			close();
		}
		
		return theTeacher;
	}

	public void updateTeacher(Teacher theTeacher) throws Exception {
		try {
		myConn = dataSource.getConnection();
		for (int i=0; i<theTeacher.getCourseNames().size(); i++)
		{
			statement = myConn.prepareCall("{call updateTeacher(?, ?, ?, ?, ?)}");
			statement.setInt(1, theTeacher.getId());
			statement.setString(2, theTeacher.getFirstName());
			statement.setString(3, theTeacher.getLastName());
			statement.setString(4, theTeacher.getCourseName(i));
			statement.setInt(5, theTeacher.getCourseId(i));
			statement.execute();
		}
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
	
	public Teacher getTeacherCourses(String theTeacherId) throws Exception {

		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("{call getAllTeachersData(?)}");
			statement.setInt(1, Integer.parseInt(theTeacherId));
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

			return new Teacher(id, firstName, lastName,email,courseIds, courseNames);
			
		}
		finally {
			// close JDBC objects
			close();
		}

	}
}