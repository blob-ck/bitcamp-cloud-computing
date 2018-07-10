package bitcamp.pms.servlet.teammember;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/team/member/delete")
public class TeamMemberDeleteServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
        String teamName = request.getParameter("teamName");
        String memberId = request.getParameter("memberId");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.printf("<meta http-equiv='Refresh' content='1;url=../view?name=%s'>\n", 
                teamName);
        out.println("<title>팀 회원 삭제</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>팀 회원 삭제 결과</h1>");
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	try (
        			Connection con = DriverManager.getConnection(
        					"jdbc:mysql://13.209.48.23:3306/studydb",
        					"study", "1111");
        			PreparedStatement stmt = con.prepareStatement(
        					"delete from pms2_team_member where tnm=? and mid=?");) {
        		
        		stmt.setString(1, teamName);
        		stmt.setString(2, memberId);
        		
        		if (stmt.executeUpdate() == 0) {
        			out.println("<p>해당 팀원이 존재하지 않습니다.</p>");
        		} else {
        			out.println("<p>팀에서 회원을 삭제하였습니다.</p>");
        		}
        	}
        } catch (Exception e) {
            out.println("<p>팀 회원 삭제 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
}
