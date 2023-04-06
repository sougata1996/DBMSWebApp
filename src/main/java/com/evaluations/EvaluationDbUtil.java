package com.evaluations;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class EvaluationDbUtil {
	
private DataSource dataSource;
	
	Connection myConn = null;
	Statement myStmt = null;
	CallableStatement statement = null;
	ResultSet myRs = null;

	public EvaluationDbUtil(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}
	
	public List<Evaluation> getEvaluations(int teacherId, int courseId) throws SQLException{
		List<Evaluation> results = new ArrayList<>();
		
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("call viewEvaluations(?, ?)");
			statement.setInt(1, teacherId);
			statement.setInt(2, courseId);
			myRs = statement.executeQuery();
			
			// process result set
			while (myRs.next()) {
				
				String eval_name = myRs.getString("eval_name");
				String eval_type = myRs.getString("eval_type");
				
				// create new student object
				Evaluation eval = new Evaluation(teacherId, courseId, eval_name, eval_type);
				
				// add it to the list of students
				results.add(eval);				
			}
			
			return results;
		}
		finally {
			// close JDBC objects
			close();
		}		
	}
	
	public boolean addEvaluationForACourse(String eval_name, String eval_type, int teacherId,
			int courseId) throws SQLException{
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("call addEvaluationForACourse(?, ?, ?, ?)");
			statement.setInt(1, teacherId);
			statement.setInt(2, courseId);
			statement.setString(3, eval_name);
			statement.setString(4, eval_type);
			myRs = statement.executeQuery();
			int count = 0;
			// process result set
			while (myRs.next()) {
				count +=1;				
			}
			if(count ==1) {
				return true;
			}
			else
				return false;
		}
		finally {
			// close JDBC objects
			close();
		}	
	}
	
	public void updateEvaluationForACourse(int teacherId, int courseId, String old_eval_name, String
			evalType, String new_eval_name) throws SQLException{
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("call updateEvaluationForACourse(?, ?, ?, ?, ?)");
			statement.setInt(1, teacherId);
			statement.setInt(2, courseId);
			statement.setString(3, old_eval_name);
			statement.setString(4, evalType);
			statement.setString(5, new_eval_name);
			myRs = statement.executeQuery();
		}
		finally {
			// close JDBC objects
			close();
		}	
		
	}
	
	public void deleteEvaluationForACourse(int teacherId, int courseId, String eval_name, String
			evalType) throws SQLException{
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("call deleteEvaluationForACourse(?, ?, ?, ?)");
			statement.setInt(1, teacherId);
			statement.setInt(2, courseId);
			statement.setString(3, eval_name);
			statement.setString(4, evalType);
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
