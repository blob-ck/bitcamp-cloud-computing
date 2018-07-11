package bitcamp.pms.listener;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bitcamp.pms.controller.MemberAddController;
import bitcamp.pms.controller.MemberDeleteController;
import bitcamp.pms.controller.MemberListController;
import bitcamp.pms.controller.MemberUpdateController;
import bitcamp.pms.controller.MemberViewController;
import bitcamp.pms.dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ContextLoaderListener 실행!!");
		
		try {
			String resource = "bitcamp/pms/config/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
			
			//@AutiWired 를 쓸 수도 있지만, 생성시에 반드시 주입되어야 하는 객체(sqlSessionFactory)는 생성자에 직접 입력하는게 낫다.
			MemberDao memberDao = new MemberDao(sqlSessionFactory);
			
			//MemberListController ctrl = new MemberListController(memberDao);
			
			ServletContext sc = sce.getServletContext();
			sc.setAttribute("/member/list", new MemberListController(memberDao));
			sc.setAttribute("/member/view", new MemberViewController(memberDao));
			sc.setAttribute("/member/update", new MemberUpdateController(memberDao));
			sc.setAttribute("/member/delete", new MemberDeleteController(memberDao));
			sc.setAttribute("/member/add", new MemberAddController(memberDao));
			//sc.setAttribute("/member/list", ctrl);
			//sc.setAttribute("memberDao", memberDao);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
