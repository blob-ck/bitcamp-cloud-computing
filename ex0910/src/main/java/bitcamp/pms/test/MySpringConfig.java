package bitcamp.pms.test;

import org.springframework.context.annotation.Bean;

public class MySpringConfig {

	@Bean("hahaha")
	public MemberDao getMemberDao() {
		return new MemberDao();
	}
	
}
