package com.courses;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.jdbc.Teacher;
import web.jdbc.TeacherDBUtil;

import java.io.IOException;

import javax.sql.DataSource;

/**
 * Servlet implementation class ViewCoursesServlet
 */
public class ViewCoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TeacherDBUtil teacherRecord;
	@Resource(name = "jdbc/scoresdb")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewCoursesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String teacherId = request.getSession().getAttribute("teacherId").toString();
		Teacher teacher = null;
		try {
			teacherRecord = new TeacherDBUtil(dataSource);
			teacher = teacherRecord.getTeacherCourses(teacherId);
			request.setAttribute("teacherId", teacherId);
			request.setAttribute("courseId_list", teacher.getCourseIds());
			request.setAttribute("courseName_list", teacher.getCourseNames());
		} catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/viewcourses_evaluations.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
