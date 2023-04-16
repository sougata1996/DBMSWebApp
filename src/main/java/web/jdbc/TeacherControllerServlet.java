package web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class TeacherControllerServlet
 */
public class TeacherControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TeacherDBUtil teacherDbUtil;
	
	@Resource(name="jdbc/scoresdb")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our db util ... and pass in the conn pool / datasource
		try {
			teacherDbUtil = new TeacherDBUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listTeachers(request, response);
				break;
				
			case "ADD":
				addTeachers(request, response);
				break;
				
			case "LOAD":
				loadTeacher(request, response);
				break;
				
			case "UPDATE":
				updateTeacher(request, response);
				break;
				
			case "DELETE":
				deleteTeacher(request, response);
				break;
				
			case "VIEW_COURSE":
				viewTeacherCourse(request, response);
				break;
				
			default:
				listTeachers(request, response);
			}
				
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
		
	}

	private void viewTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String id = request.getParameter("teacherId");
		Teacher ob = teacherDbUtil.getTeacherCourses(id);
		// add students to the request
		request.setAttribute("TEACHER_COURSE_LIST", ob);
						
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view_teacher_course.jsp");
		dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		int id = Integer.parseInt(request.getParameter("teacherId"));
		teacherDbUtil.deleteTeacher(id);
		listTeachers(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		int id = Integer.parseInt(request.getParameter("teacherId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String courseNames = request.getParameter("courseNames");
		String[] arr = courseNames.split(",");
		List<String> list = Arrays.asList(arr);
		
		String courseIds = request.getParameter("courseIds");
		String[] arr_2 = courseIds.split(",");
		List<Integer> list_2 = new ArrayList<>();
		for (String courseId : arr_2) {
			list_2.add(Integer.parseInt(courseId.replaceAll("[^0-9]", "")));
		}
		
		// create a new teacher object
		Teacher theTeacher = new Teacher(id, firstName, lastName, list, list_2);
		
		// perform update on database
		teacherDbUtil.updateTeacher(theTeacher);
		
		// send them back to the "list teachers" page
		listTeachers(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void loadTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String theTeacherId = request.getParameter("teacherId");
				
		// get teacher from database (db util)
		Teacher theTeacher = teacherDbUtil.getTeacher(Integer.parseInt(theTeacherId));
		
		// place teacher in the request attribute
		request.setAttribute("THE_TEACHER", theTeacher);
		
		// send to jsp page: update-teacher-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-teacher-form.jsp");
		dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void addTeachers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		int id = Integer.parseInt(request.getParameter("Id"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");	
		Integer courseId = Integer.parseInt(request.getParameter("courseId"));
		String courseName = request.getParameter("courseName");
		
		Teacher theTeacher = new Teacher(id, firstName, lastName, email, courseId, courseName);
		
		// add the teacher to the database
		teacherDbUtil.addTeacher(theTeacher);
		
		// send back to main page (the teacher list)
			listTeachers(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void listTeachers(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		try {
		// get teachers from db util
		List<Teacher> teachers = teacherDbUtil.getTeachers();
		
		// add teachers to the request
		request.setAttribute("TEACHER_LIST", teachers);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-teachers.jsp");
		dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}
}
