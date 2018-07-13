package bitcamp.pms.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

/*@ComponentScan(basePackages = {"bitcamp.pms"},
excludeFilters = @Filter(type=FilterType.REGEX, pattern="bitcamp.pms.test.*"))*/
@Configuration
public class AppConfig {

	public AppConfig() {
		System.out.println("AppConfig() 호출됨!");
	}
	
	// Spring IoC Container가 보관할 객체를 여기서 만들어보자
	/*@Bean("sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory() throws Exception {
		System.out.println("AppConfig.getSqlSessionFactory 호출됨!");
		String resource = "bitcamp/pms/config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
			
		return new SqlSessionFactoryBuilder().build(inputStream);
	}*/
}
