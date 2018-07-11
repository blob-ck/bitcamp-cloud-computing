package bitcamp.pms;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		String path = request.getPathInfo();
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.include(request, response);
		
		String view = (String)request.getAttribute("view");
		
		if (view != null && view.startsWith("redirect:")) {
			response.sendRedirect(view.substring(9));
		} else if (view != null) {
			rd = request.getRequestDispatcher(view);
			rd.include(request, response);
		} else {
			rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}
	}
}
