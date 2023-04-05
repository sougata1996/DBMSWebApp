package com.evaluations;

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
 * Servlet implementation class EvaluationControllerServlet
 */
public class EvaluationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EvaluationDbUtil evaluation;
	
	@Resource(name="jdbc/scoresdb")
	private DataSource dataSource;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		evaluation = new EvaluationDbUtil(dataSource);
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
				listEvaluation(request, response);
				break;
				
			case "ADD":
				request.getSession().setAttribute("courseId", request.getParameter("courseId"));
				RequestDispatcher dispatcher = request.getRequestDispatcher("/add-evaluation.jsp");
				dispatcher.forward(request, response);
				break;
			case "INSERT":
				addEvaluation(request, response);
			default:
				listEvaluation(request, response);
			}
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void addEvaluation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String eval_type = request.getParameter("eval_type");
		String eval_name = request.getParameter("eval_name");
		int teacher_id = Integer.parseInt(request.getSession().getAttribute("teacherId").toString());
		int courseId = Integer.parseInt(request.getSession().getAttribute("courseId").toString());
		
		evaluation.addEvaluationForACourse(eval_name, eval_type, teacher_id, courseId);
		try {
			listEvaluation(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listEvaluation(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Evaluation> evaluations = evaluation.getEvaluations(Integer.parseInt(request.getSession().getAttribute("teacherId").toString()),
				Integer.parseInt(request.getSession().getAttribute("courseId").toString()));
		
		// add students to the request
		request.setAttribute("eval_list", evaluations);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-evaluations.jsp");
		dispatcher.forward(request, response);
	}
}

