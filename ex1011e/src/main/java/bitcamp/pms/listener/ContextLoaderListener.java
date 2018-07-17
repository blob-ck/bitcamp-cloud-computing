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
			ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext("bitcamp/pms/config/application-context.xml"); 
			
			//자바쪽에서 sqlSessionFactory를 생성했는지 확인해보자
			String[] names = iocContainer.getBeanDefinitionNames();
			for (String name : names) {
				System.out.printf("%s ==> %s\n", name, iocContainer.getBean(name).getClass().getName());
			}
			
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
