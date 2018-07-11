package bitcamp.pms.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.controller.PageController;

@SuppressWarnings("serial")
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println(request.getServletPath());
		System.out.println(request.getPathInfo());
		
		//클라이언트가 요청한 url을 알아낸다
		//즉 app/* 에서 *에 해당하는 값을 추출한다.
		String pathInfo = request.getPathInfo();
		
		response.setContentType("text/html;charset=UTF-8");
		
		//ServletContext 보관소에 저장된 페이지 컨트롤러를 찾는다.
		PageController pageController = (PageController)request.getServletContext().getAttribute(pathInfo);
		
		//페이지 컨트롤러가 실행을 끝낸 후 view 이름으로 저장한 jsp를 실행한다. 예) 해당 서블릿에서  request.setAttribute("view", "/member/list.jsp"); 
		//String view = (String)request.getAttribute("view");
		
		//페이지 컨트롤러를 실행한다.
		try {
			if (pageController == null) {
				throw new Exception("해당 URL에 대해 서비스를 처리할 수 없습니다.");
			}
			
			String view = pageController.service(request, response);
			if (view.startsWith("redirect:")) {
				response.sendRedirect(view.substring(9));
			} else if (view != null) {
				//다른 페이지 컨트롤러로 위임한다.
				RequestDispatcher rd = request.getRequestDispatcher(view);
				rd.include(request, response);
			} 
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}
	}
}
