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

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.dao.TaskDao;
import bitcamp.pms.dao.TeamDao;
import bitcamp.pms.dao.TeamMemberDao;

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
			
			MemberDao memberDao = new MemberDao(sqlSessionFactory);
			
			/*MemberDao memberDao = new MemberDao(
					"jdbc:mysql://13.209.48.23:3306/studydb", "study", "1111");*/
			TeamDao teamDao = new TeamDao(
					"jdbc:mysql://13.209.48.23:3306/studydb", "study", "1111");
			TeamMemberDao teamMemberDao = new TeamMemberDao(
					"jdbc:mysql://13.209.48.23:3306/studydb", "study", "1111");
			TaskDao taskDao = new TaskDao(
					"jdbc:mysql://13.209.48.23:3306/studydb", "study", "1111");
			ServletContext sc = sce.getServletContext();
			sc.setAttribute("memberDao", memberDao);
			sc.setAttribute("teamDao", teamDao);
			sc.setAttribute("teamMemberDao", teamMemberDao);
			sc.setAttribute("taskDao", taskDao);
			//sc.setAttribute("boardDao", boardDao);
			//sc.setAttribute("classroomDao", classroomDao);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
