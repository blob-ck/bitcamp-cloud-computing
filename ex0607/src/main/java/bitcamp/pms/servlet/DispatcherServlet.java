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
		
		String pathInfo = request.getPathInfo();
		
		response.setContentType("text/html;charset=UTF-8");
		
		/*//다른 서블릿(페이지 컨트롤러)으로 위임한다.
		RequestDispatcher rd = request.getRequestDispatcher(pathInfo);
		//모든 서블릿을 include했다.
		rd.include(request, response);*/
		
		//ContextLoaderListener 에서 pathInfo와 일치하는 context의 attribute명을 가진 객체를 주입한다. 
		//서블릿 전역스코프인 ServletContext를 얻어 생성했다.
		PageController pageController = (PageController)getServletContext().getAttribute(pathInfo);
		
		//페이지 컨트롤러가 실행을 끝낸 후 view 이름으로 저장한 jsp를 실행한다. 예) 해당 서블릿에서  request.setAttribute("view", "/member/list.jsp"); 
		//String view = (String)request.getAttribute("view");
		
		try {
			if (pageController == null) {
				throw new Exception("해당 URL에 대해 섭수가 없음다.");
			}
			
			String view = pageController.service(request, response);

			if (view != null && view.startsWith("redirect:")) {
				response.sendRedirect(view.substring(9));
			} else if (view != null) {
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
