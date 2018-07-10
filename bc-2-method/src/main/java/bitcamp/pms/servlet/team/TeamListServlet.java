package bitcamp.pms.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.domain.Team;

@SuppressWarnings("serial")
@WebServlet("/team/list")
public class TeamListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>팀 목록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>팀 목록</h1>");
        
        try {
        	out.println("<p><a href='form.html'>새 팀</a></p>");
        	out.println("<table border='1'>");
        	out.println("<tr>");
        	out.println("    <th>팀명</th><th>최대인원</th><th>기간</th>");
        	out.println("</tr>");
        	
        	ArrayList<Team> list = selectList();
            
    		for (Team team : list) {
            	out.println("<tr>");
            	out.printf("<td><a href='view?name=%s'>%s</a></td><td>%d</td><td>%s~%s</td>\n",
            			team.getName(),
            			team.getName(),
            			team.getMaxQty(), 
            			team.getStartDate(), 
            			team.getEndDate());
            	out.println("</tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            out.println("<p>목록 가져오기 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
	
	
	
	
	private ArrayList<Team> selectList() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
        try (
            Connection con = DriverManager.getConnection(
        		"jdbc:mysql://13.209.48.23:3306/studydb",
                "study", "1111");
            PreparedStatement stmt = con.prepareStatement(
                "select name, sdt, edt, max_qty from pms2_team");
            ResultSet rs = stmt.executeQuery();) {
            
        	ArrayList<Team> list = new ArrayList<>();
        	
    		while (rs.next()) {
    			Team team = new Team();
    			team.setName(rs.getString("name"));
    			team.setMaxQty(rs.getInt("max_qty"));
    			team.setStartDate(rs.getDate("sdt"));
    			team.setEndDate(rs.getDate("edt"));
            	
    			list.add(team);
            }
    		return list;
        }
	}
}
