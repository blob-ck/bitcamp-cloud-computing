package bitcamp.pms.test;

import org.springframework.context.annotation.Bean;

// JAVA Class 파일로 config
public class MySpringConfig {

	@Bean("okok")
	public MemberDao getMemberDao() {
		
		return new MemberDao();
	}

}
