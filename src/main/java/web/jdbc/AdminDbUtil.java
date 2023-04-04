package web.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;

public class AdminDbUtil {
	
	private DataSource dataSource;
	
	Connection myConn = null;
	Statement myStmt = null;
	CallableStatement statement = null;
	ResultSet myRs = null;

	public AdminDbUtil(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}

	public boolean checkCredentials(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		boolean myResult = false;
		try {
			myConn = dataSource.getConnection();
			statement = myConn.prepareCall("SELECT readAdminCredentials(?, ?)");
			statement.setString(1, userName);
			statement.setString(2, password);
			myRs = statement.executeQuery();
			if (myRs.next()) {
			   myResult = myRs.getBoolean(1);
			}
			
			}
			finally {
				close();
			}
		
		return myResult;
	}

	private void close() {
		// TODO Auto-generated method stub
		
	}
}
