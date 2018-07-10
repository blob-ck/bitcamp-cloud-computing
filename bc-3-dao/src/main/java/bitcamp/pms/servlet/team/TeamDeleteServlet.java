package bitcamp.pms.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.TaskDao;
import bitcamp.pms.dao.TeamDao;
import bitcamp.pms.dao.TeamMemberDao;

@SuppressWarnings("serial")
@WebServlet("/team/delete")
public class TeamDeleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta http-equiv='Refresh' content='1;url=list'>");
        out.println("<title>팀 삭제</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>팀 삭제 결과</h1>");
        
        try {
        	//team_member
        	TeamMemberDao teamMemberDao = (TeamMemberDao)getServletContext().getAttribute("teamMemberDao");
        	teamMemberDao.delete(name);
        	
        	//task
        	TaskDao taskDao = (TaskDao)getServletContext().getAttribute("taskDao");
        	taskDao.deleteByTeam(name);
        	
        	//team
        	TeamDao teamDao = (TeamDao)getServletContext().getAttribute("teamDao");
        	int count = teamDao.delete(name);
        	
    		if (count == 0) {
                out.println("<p>해당 팀이 없습니다.</p>");
            } else {
                out.println("<p>삭제하였습니다.</p>");
            }
        } catch (Exception e) {
            out.println("<p>삭제 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
}
