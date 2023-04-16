package com.teacher;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.sql.DataSource;

import com.credentials.CredentialsDbUtil;

/**
 * Servlet implementation class TeacherLoginServlet
 */
public class TeacherLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CredentialsDbUtil teacherLogin;
	@Resource(name = "jdbc/scoresdb")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherLoginServlet() {
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
			teacherLogin = new CredentialsDbUtil(dataSource);
			boolean result = teacherLogin.validateteacherCredential(username, password);
			if (result) {
				request.getSession().setAttribute("teacherId", username);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher_landingpage.jsp");
				dispatcher.forward(request, response);
			} 
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher_login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher_login.jsp");
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
