package com.students;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.jdbc.Student;
import web.jdbc.StudentDbUtil;

import java.io.IOException;

import javax.sql.DataSource;

import com.credentials.CredentialsDbUtil;

/**
 * Servlet implementation class StudentLoginServlet
 */
public class StudentLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CredentialsDbUtil studentLogin;
	private StudentDbUtil studentRecord;
	@Resource(name = "jdbc/scoresdb")
	private DataSource dataSource;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentLoginServlet() {
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
		Student student = null;
		try {
			studentLogin = new CredentialsDbUtil(dataSource);
			boolean result = studentLogin.validateStudentCredential(username, password);
			if (result) {
				request.getSession().setAttribute("studentId", username);
				studentRecord = new StudentDbUtil(dataSource);
				student = studentRecord.getStudentCourses(request.getSession().getAttribute("studentId").toString());
				request.setAttribute("studentId", username);
				request.setAttribute("courseId_list", student.getCourseIds());
				request.setAttribute("courseName_list", student.getCourseNames());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/student_landingpage.jsp");
				dispatcher.forward(request, response);
			} 
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/student_login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/student_login.jsp");
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
