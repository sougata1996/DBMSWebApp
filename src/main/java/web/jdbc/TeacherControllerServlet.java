package web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
				
			default:
				listTeachers(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}	
		
	}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("teacherId"));
		teacherDbUtil.deleteTeacher(id);
		listTeachers(request, response);
	}

	private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("teacherId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String courseName = request.getParameter("courseName");
		
		// create a new student object
		Teacher theTeacher = new Teacher(id, firstName, lastName, courseName);
		
		// perform update on database
		teacherDbUtil.updateTeacher(theTeacher);
		
		// send them back to the "list students" page
		listTeachers(request, response);
	}

	private void loadTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String theTeacherId = request.getParameter("teacherId");
				
		// get student from database (db util)
		Teacher theTeacher = teacherDbUtil.getTeacher(theTeacherId);
		
		// place student in the request attribute
		request.setAttribute("THE_TEACHER", theTeacher);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-teacher-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addTeachers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int id = Integer.parseInt(request.getParameter("Id"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");	
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		String courseName = request.getParameter("courseName");
		
		Teacher theTeacher = new Teacher(id, firstName, lastName, email, courseId, courseName);
		
		// add the student to the database
		teacherDbUtil.addTeacher(theTeacher);
		
		// send back to main page (the student list)
		try {
			listTeachers(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listTeachers(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Teacher> teachers = teacherDbUtil.getTeachers();
		
		// add students to the request
		request.setAttribute("TEACHER_LIST", teachers);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-teachers.jsp");
		dispatcher.forward(request, response);
	}
}