package bitcamp.pms.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext("bitcamp/pms/config/application-config.xml");
		//AnnotationConfigApplicationContext iocContainer = new AnnotationConfigApplicationContext(MySpringConfig.class);
		System.out.println("Generated beans : "+iocContainer.getBeanDefinitionCount());
		String[] names = iocContainer.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println("--------------------------------------------------------------------------------------------------------");
			System.out.printf("object name : %s\nClass name : %s\n",name, iocContainer.getBean(name).toString());
		}
		
	}
}
