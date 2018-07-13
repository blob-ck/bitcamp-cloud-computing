package bitcamp.pms.listener;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@WebListener
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ContextLoaderListener 실행!!");
		
		try {
			ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext("bitcamp/pms/config/application-context.xml"); 
			
			/*String resource = "bitcamp/pms/config/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
			
			iocContainer.addBean("sqlSessionFactory", sqlSessionFactory);
			iocContainer.refresh();
			*/
			
			//프론트 컨트롤러가 사용할 수 있도록 IoC 컨테이너를 
			//컨텍스트에 저장한다.
			ServletContext sc = sce.getServletContext();
			sc.setAttribute("iocContainer", iocContainer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
