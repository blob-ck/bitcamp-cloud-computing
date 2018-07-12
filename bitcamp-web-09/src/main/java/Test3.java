import bitcamp.pms.container.ApplicationContext;

public class Test3 {

	public static void main(String[] args) throws Exception {

		
		//객체를 자동 생성할 패키지를 지정하여 컨테이너를 만든다
		ApplicationContext iocContainer = new ApplicationContext("bitcamp.pms");
		
		//요청에서 넘어올 url 을 파라미터로 넘기면 일치하는 어노테이션명을 가진 컨트롤러 객체를 호출한다.
		Object obj = iocContainer.getBean("/member/add");
		System.out.println(obj.getClass().getName());
		
	}
}
