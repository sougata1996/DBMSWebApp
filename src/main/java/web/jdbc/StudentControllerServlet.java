package web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet.
 * It acts as the controller and mediates calls between the DB Util and the JSP front end view.
 */
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil StudentDbUtil;
	
	@Resource(name="jdbc/scoresdb")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our db util ... and pass in the conn pool / datasource
		try {
			StudentDbUtil = new StudentDbUtil(dataSource);
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
				listStudents(request, response);
				break;
				
			case "ADD":
				addStudents(request, response);
				break;
				
			case "LOAD":
				loadStudent(request, response);
				break;
				
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;
				
			case "VIEW_COURSE":
				viewStudentCourse(request, response);
				break;
			case "STUDENT_COURSE_LIST":
				listStudentsInACourse(request, response);
				break;
				
			default:
				listStudents(request, response);
			}
				
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}		
	}
	
	private void viewStudentCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String id = request.getParameter("studentId");
		Student ob = StudentDbUtil.getStudentCourses(id);
		// add students to the request
		request.setAttribute("STUDENT_COURSE_LIST", ob);
						
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view_student_course.jsp");
		dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		int id = Integer.parseInt(request.getParameter("studentId"));
		StudentDbUtil.deleteStudent(id);
		listStudents(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		// create a new student object
		Student theStudent = new Student(id, firstName, lastName);
		
		// perform update on database
		StudentDbUtil.updateStudent(theStudent);
		
		// send them back to the "list students" page
		listStudents(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
		String theStudentId = request.getParameter("studentId");
				
		// get student from database (db util)
		Student theStudent = StudentDbUtil.getStudent(theStudentId);
		
		// place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
		int id = Integer.parseInt(request.getParameter("Id"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");	
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		Student theStudent = new Student(id, firstName, lastName, email, courseId);
		
		// add the student to the database
		StudentDbUtil.addStudent(theStudent);
		
		// send back to main page (the student list)
		listStudents(request, response);
		} catch (Exception e) {
		    request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		try {
		// get students from db util
		List<Student> Students = StudentDbUtil.getStudents();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", Students);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}
	
	private void listStudentsInACourse(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		try {
			String courseId = request.getParameter("courseId");
			request.getSession().setAttribute("courseId", courseId);

			// get students from db util
			List<Student> Students = StudentDbUtil.getStudentsInACourse(
					Integer.parseInt(courseId));
			
			// add students to the request
			request.setAttribute("STUDENT_LIST", Students);
					
			// send to JSP page (view)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-course-students.jsp");
			dispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}
}