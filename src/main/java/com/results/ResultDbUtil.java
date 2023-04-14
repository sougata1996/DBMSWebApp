package com.results;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ResultDbUtil {
	private DataSource dataSource;

	Connection myConn = null;
	Statement myStmt = null;
	CallableStatement statement = null;
	ResultSet myRs = null;
	
	public ResultDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Results> getResultsFromAcourse(int student_id, int course_id) throws Exception{
		List<Results> results = new ArrayList<>();
		try {
			myConn = dataSource.getConnection();
			String sql = "call viewResults(?, ?)";
			statement = myConn.prepareCall(sql);
			statement.setInt(1, student_id);
			statement.setInt(2, course_id);
			myRs = statement.executeQuery();
			while(myRs.next()) {
				int score = myRs.getInt("score");
				String eval_name = myRs.getString("eval_name");
				String eval_type = myRs.getString("eval_type");
				Results result = new Results(score, eval_name, eval_type,
						student_id, course_id);
				results.add(result);
			}
		return results;
		}
		finally {
			// close JDBC objects
			close();
		}		
	}
	
	public void addResult(int score, String eval_name, String eval_type, 
			int course_id, int student_id) throws Exception{
		try {
			myConn = dataSource.getConnection();
			String sql = "call addResult(?, ?, ?, ?, ?)";
			statement = myConn.prepareCall(sql);
			statement.setInt(1, score);
			statement.setString(2, eval_name);
			statement.setString(3, eval_type);
			statement.setInt(4, course_id);
			statement.setInt(5, student_id);
			myRs = statement.executeQuery();
		}
		finally {
			// close JDBC objects
			close();
		}	
		
	}
	public void deleteResult(String eval_type, String eval_name, 
			int course_id, int student_id) throws Exception{
		try {
			myConn = dataSource.getConnection();
			String sql = "call deleteResult(?, ?, ?, ?)";
			statement = myConn.prepareCall(sql);
			statement.setString(1, eval_type);
			statement.setString(2, eval_name);
			statement.setInt(3, course_id);
			statement.setInt(4, student_id);
			myRs = statement.executeQuery();
		}
		finally {
			// close JDBC objects
			close();
		}	
		
	}
	public void updateResult(int newScore, String eval_name, String eval_type, 
			int course_id, int student_id) throws Exception{
		try {
			myConn = dataSource.getConnection();
			String sql = "call updateResult(?, ?, ?, ?, ?)";
			statement = myConn.prepareCall(sql);
			statement.setInt(1, newScore);
			statement.setString(2, eval_name);
			statement.setString(3, eval_type);
			statement.setInt(4, course_id);
			statement.setInt(5, student_id);
			myRs = statement.executeQuery();
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
	
}