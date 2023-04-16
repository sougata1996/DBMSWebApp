package web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.sql.DataSource;

/**
 * Servlet implementation class CredentialsServlet.
 * It acts as the controller in this MVC design.
 * The servlet makes a call to the DB Util and forwards the response to the JSP
 * via the request object.
 */
public class CredentialsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AdminDbUtil AdminDbUtil;

	@Resource(name = "jdbc/scoresdb")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Set the content type to plain text
        /*response.setContentType("text/plain");

        // Get the output stream for the response
        OutputStream outputStream = response.getOutputStream();
        
        // Create a PrintStream to write plain text to the output stream
        PrintStream printStream = new PrintStream(outputStream);*/
        
		try {
			AdminDbUtil = new AdminDbUtil(dataSource);
			boolean result = AdminDbUtil.checkCredentials(username, password);
			 // Write the plain text to the PrintStream
	        //printStream.println(username + "," + password);
			if (result) {
				// send to JSP page (view)
				RequestDispatcher dispatcher = request.getRequestDispatcher("/admin-login-response.jsp");
				dispatcher.forward(request, response);
			} else {
				// send to JSP page (view)
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}

	}

}