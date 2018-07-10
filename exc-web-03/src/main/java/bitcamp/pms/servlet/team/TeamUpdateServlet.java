package bitcamp.pms.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.TeamDao;
import bitcamp.pms.domain.Team;

@SuppressWarnings("serial")
@WebServlet("/team/update")
public class TeamUpdateServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		Team team = new Team();
		team.setName(request.getParameter("name"));
		team.setDescription(request.getParameter("description"));
		team.setMaxQty(Integer.parseInt(request.getParameter("maxQty")));
        team.setStartDate(Date.valueOf(request.getParameter("startDate")));
        team.setEndDate(Date.valueOf(request.getParameter("endDate")));
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta http-equiv='Refresh' content='1;url=list'>");
        out.println("<title>팀 변경</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>팀 변경 결과</h1>");
        
        try {
        	TeamDao teamDao = (TeamDao)getServletContext().getAttribute("teamDao");
        	int count = teamDao.update(team);
            if (count == 0) {
            	out.println("<p>해당 팀이 존재하지 않습니다.</p>");
            } else {
                out.println("<p>변경하였습니다.</p>");
            }
        } catch (Exception e) {
            out.println("<p>변경 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
}
