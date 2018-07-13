package bitcamp.pms.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ContextLoaderListener 실행!!");
		
		try {
			ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext("bitcamp/pms/config/application-config.xml"); 
			
			ServletContext sc = sce.getServletContext();
			sc.setAttribute("iocContainer", iocContainer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
