package com.credentials;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class CredentialsDbUtil {
private DataSource dataSource;
	
	Connection myConn = null;
	Statement myStmt = null;
	CallableStatement statement = null;
	ResultSet myRs = null;

	public CredentialsDbUtil(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}
	
	public boolean insertStudentCredential(int id, String password) throws SQLException {
		
		try {
			
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("call insertStudentLogin(?, ?)");
			statement.setInt(1, id);
			statement.setString(2, password);
			myRs = statement.executeQuery();
			int count = 0;
			while(myRs.next()) {
				count+=1;
			}
			if(count == 1) {
				return true;
			}
			else
				return false;
		}
		finally {
			close();
		}
	}
	public boolean insertTeacherCredential(int id, String password) throws SQLException {
		
		try {
			
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("call insertTeacherLogin(?, ?)");
			statement.setInt(1, id);
			statement.setString(2, password);
			myRs = statement.executeQuery();
			int count = 0;
			while(myRs.next()) {
				count+=1;
			}
			if(count == 1) {
				return true;
			}
			else
				return false;
		}
		finally {
			close();
		}
	}
	public boolean validateteacherCredential(int id, String password) throws SQLException {
		
		try {
			boolean myResult = false;
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("select validateTeacherLogin(?, ?)");
			statement.setInt(1, id);
			statement.setString(2, password);
			myRs = statement.executeQuery();
			if (myRs.next()) {
				   myResult = myRs.getBoolean(1);
				}
			return myResult;
		}
		finally {
			close();
		}
	}	
	public boolean validateStudentCredential(int id, String password) throws SQLException {
		
		try {
			boolean myResult = false;
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("select validateStudentLogin(?, ?)");
			statement.setInt(1, id);
			statement.setString(2, password);
			myRs = statement.executeQuery();
			if (myRs.next()) {
				   myResult = myRs.getBoolean(1);
				}
			return myResult;
		}
		finally {
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
