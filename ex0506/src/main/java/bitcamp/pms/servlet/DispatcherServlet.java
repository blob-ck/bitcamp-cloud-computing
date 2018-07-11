package bitcamp.pms.servlet;

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
	
	@SuppressWarnings("unused")
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//모든 서블릿에서 실행되고 있는 RequestDispatcher를 여기에서 몰아 쓰고,
		//이름을 부르기만 하고 업무는 위임한다. include() 사용 -> model
		//결과를 다시 뷰와 합쳐서 만든 뒤 응답한다. include() 사용 -> view
		
		//이 서블릿이 최후에 응답을 하므로 response 인코딩 설정을 한다
		response.setContentType("text/html;charset=UTF-8");
		
		String path1 = request.getContextPath(); // /프로젝트명
		String path2 = request.getServletPath();// 	/app
		String path3 = request.getPathInfo(); //		/app 이후의 경로 (* 에 해당하는 경로가 나온다)
		
		// /app/member/list 라면 /member/list 가 path3에 저장되어있으므로 해당하는 서블릿을 실행한다
		RequestDispatcher rd = request.getRequestDispatcher(path3);
		
		//path3에 해당하는 서블릿을 실행하여 결과를 request에 담아왔다.
		//request에는 DB에서 가져온 model이 있고, view의 이름도 String으로 담겨있다
		rd.include(request, response); 
		
		
		//model servlet쪽에서 넘어온 view의 이름으로 해당 서블릿이나 jsp를 호출하여 view를 완성한다
		//request.getParameter() 와 헷갈리지 말자
		String view = (String)request.getAttribute("view");
		
		if (view != null && view.startsWith("redirect:")) {
			//리다이렉트로 보내는 경우 모델쪽에서 redirect: 로 시작하도록 작성했으므로 조건문에서 거른다
			response.sendRedirect(view.substring(9));
		} else if (view != null) {
			//리다이렉트가 아닌 경우 뷰를 include해서 처리한다
			rd = request.getRequestDispatcher(view);
			rd.include(request, response);
		} else {
			rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}
	}
}
