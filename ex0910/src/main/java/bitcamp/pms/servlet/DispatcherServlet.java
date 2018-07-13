package bitcamp.pms.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import bitcamp.pms.annoation.RequestMapping;

@SuppressWarnings("serial")
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("dispatcher");

		//클라이언트가 요청한 url을 알아낸다
		//즉 app/* 에서 *에 해당하는 값을 추출한다.
		String pathInfo = request.getPathInfo();
		System.out.println("pathInfo = " +pathInfo);
		
		response.setContentType("text/html;charset=UTF-8");
		
		//ServletContext 보관소에 저장된 IoC 컨트롤러를 찾는다.
		ApplicationContext iocContainer = (ApplicationContext) getServletContext().getAttribute("iocContainer");
		
		System.out.println(iocContainer==null);
		try {
			
			//iocContainer 보관소에 저장된 페이지 컨트롤러를 찾는다.
			Object pageController = iocContainer.getBean(pathInfo);
			
			if (pageController == null) {
				throw new Exception("해당 URL에 대해 서비스를 처리할 수 없습니다.");
			}
			
			//페이지 컨트롤러를 실행한다.
			//페이지 컨트롤러에 있는 메서드 중에서 @RequestMapping 이 붙은 걸 찾아서 호출한다
			Method requestHandler = getRequestHandler(pageController.getClass());
			
			if (requestHandler == null) {
				throw new Exception("요청 핸들러를 찾지 못했습니다.");
			}
			
			//페이지 컨트롤러의 메서드를 호출한다.
			// invoke(객체주소, 파라미터)
			//이런걸 Reflection 이라 한다.
			String view = (String)requestHandler.invoke(pageController, request, response);
			
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

	
	
	
	private Method getRequestHandler(Class<?> clazz) {

		//클래스 정보에서 메서드 정보를 추출한다.
		Method[] methods = clazz.getMethods();
		
		//메서드 중에서 @RequestMapping이 붙은 메서드를 찾아낸다
		//있으면 RequestMappin 객체를 리턴, 없으면 null 리턴
		for (Method m : methods) {
			RequestMapping anno = m.getAnnotation(RequestMapping.class); //annotation 타입을 파라미터로 보내야한다.
			if (anno != null) {
				return m;
			}
		}
		return null;
	}
}
