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
			if(request.getParameter("courseId")!=null) {
				request.getSession().setAttribute("courseId", request.getParameter("courseId"));
			}
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listEvaluation(request, response);
				break;	
			case "ADD":
				RequestDispatcher dispatcher = request.getRequestDispatcher("/add-evaluation.jsp");
				dispatcher.forward(request, response);
				break;
			case "INSERT":
				addEvaluation(request, response);
				break;
			case "LOAD":
				loadEvaluation(request, response);
			case "UPDATE":
				updateEvaluation(request, response);
				break;
			case "DELETE":
				deleteEvaluation(request, response);
				break;
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
		;
		// add students to the request
		request.setAttribute("eval_list", evaluations);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-evaluations.jsp");
		dispatcher.forward(request, response);
	}
	private void loadEvaluation(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Evaluation eval = new Evaluation(Integer.parseInt(request.getParameter("teacher_id")),Integer.parseInt(request.getParameter("course_id")),
				request.getParameter("eval_name"),request.getParameter("eval_type"));
		
		request.setAttribute("evaluation", eval);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-evaluations.jsp");
		dispatcher.forward(request, response);		
	
	}
	private void updateEvaluation(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		int teacher_id = Integer.parseInt(request.getSession().getAttribute("teacherId").toString());
		int course_id = Integer.parseInt(request.getSession().getAttribute("courseId").toString());
		String old_eval_name = request.getParameter("old_eval_name");
		String eval_type = request.getParameter("eval_type");
		String new_eval_name = request.getParameter("new_eval_name");
		
		evaluation.updateEvaluationForACourse(teacher_id, course_id, old_eval_name, eval_type, new_eval_name);
		 
		listEvaluation(request, response);
	
	}
	private void deleteEvaluation(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		int teacher_id = Integer.parseInt(request.getSession().getAttribute("teacherId").toString());
		int course_id = Integer.parseInt(request.getSession().getAttribute("courseId").toString());
		String eval_type = request.getParameter("eval_type");
		String eval_name = request.getParameter("eval_name");
		
		evaluation.deleteEvaluationForACourse(teacher_id, course_id, eval_name, eval_type);
		
		listEvaluation(request, response);
	}
}

