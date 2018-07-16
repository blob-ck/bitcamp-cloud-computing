package bitcamp.pms.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// XML 파일로 config  (간접)
public class Test4 {

	public static void main(String[] args) throws Exception {

		
		//@Autuwired 메소드를 찾아 AutowiredAnnotationBeanPostProcessor 가 등록하면 밑에놈 ClassPathXmlApplicationContext 가 꺼내 쓴다.
		ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext("bitcamp/pms/test/application-context4.xml");
		
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		System.out.printf("count produced Object : %s\n", iocContainer.getBeanDefinitionCount());
		String[] names = iocContainer.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			System.out.printf("Object name : %s\nClass name : %s\n", name, 
					iocContainer.getBean(name).toString());
		}
	}
}
