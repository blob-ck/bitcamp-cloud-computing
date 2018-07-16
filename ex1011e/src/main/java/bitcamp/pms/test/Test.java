package bitcamp.pms.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// XML 파일로 config
public class Test {

	public static void main(String[] args) throws Exception {

		ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext("bitcamp/pms/test/application-context.xml");
		
		System.out.println(iocContainer.getBeanDefinitionCount());
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		String[] names = iocContainer.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(
					iocContainer.getBean(name).getClass().getName());
		}
	}
}
