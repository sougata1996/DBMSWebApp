package com.credentials;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentSignupServlet
 */
public class StudentSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CredentialsDbUtil studentCred;
	@Resource(name = "jdbc/scoresdb")
	private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentSignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int username = Integer.parseInt(request.getParameter("username"));
		String password = request.getParameter("password");
		
        
		try {
			studentCred = new CredentialsDbUtil(dataSource);
			boolean result = studentCred.insertStudentCredential(username, password);
			if (result) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/signup-success.html");
				dispatcher.forward(request, response);
			} 
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/signup-fail.html");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/signup-fail.html");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
