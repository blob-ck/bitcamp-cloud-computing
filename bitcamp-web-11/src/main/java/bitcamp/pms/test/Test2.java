package bitcamp.pms.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//자바 클래스파일로 config
public class Test2 {

	public static void main(String[] args) throws Exception {

		AnnotationConfigApplicationContext iocContainer = new AnnotationConfigApplicationContext(MySpringConfig.class);
		
		System.out.println(iocContainer.getBeanDefinitionCount());
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		String[] names = iocContainer.getBeanDefinitionNames();
		for (String name : names) {
			System.out.printf("%s ==> %s\n", name,
					iocContainer.getBean(name).getClass().getName());
		}
	}
}
