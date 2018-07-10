package bitcamp.pms.servlet.teammember;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/team/member/add")
public class TeamMemberAddServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
        out.println("<title>팀 회원 등록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>팀 회원 등록 결과</h1>");
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            try (
	                Connection con = DriverManager.getConnection(
	                		"jdbc:mysql://13.209.48.23:3306/studydb",
	    					"study", "1111");
            	) {
                
            	PreparedStatement stmt1 = con.prepareStatement(
            			"select dscrt, sdt, edt, max_qty from pms2_team where name=?");
                stmt1.setString(1, teamName);
                try (ResultSet rs = stmt1.executeQuery();) {
                    if (!rs.next()) {
                    	out.println(teamName + " 팀은 존재하지 않습니다.");
                    	return;
                    }
                }
            }
            
            
            try (
	                Connection con = DriverManager.getConnection(
	                		"jdbc:mysql://13.209.48.23:3306/studydb",
	    					"study", "1111");
            	) {
                PreparedStatement stmt2 = con.prepareStatement(
                		"select mid,email from pms2_member where mid=?");
                stmt2.setString(1, memberId);
                try (ResultSet rs = stmt2.executeQuery();) {
                    if (!rs.next()) {
                    	out.println(memberId + " 회원은 없습니다.");
                    	return;
                    }
                }
            }
            
            
            try (
	                Connection con = DriverManager.getConnection(
	                		"jdbc:mysql://13.209.48.23:3306/studydb",
	    					"study", "1111");
            	) {
                    
                PreparedStatement stmt3 = con.prepareStatement(
                		"select mid from pms2_team_member where tnm=? and mid=?");
                stmt3.setString(1, teamName);
                stmt3.setString(2, memberId);
                try (ResultSet rs = stmt3.executeQuery()) {
                    if (rs.next()) {
                    	out.println("이미 등록된 회원입니다.");
                    	return;
                    } 
                }
            }
            

            try (
	                Connection con = DriverManager.getConnection(
	                		"jdbc:mysql://13.209.48.23:3306/studydb",
	    					"study", "1111");
            	) {
            	PreparedStatement stmt4 = con.prepareStatement(
                        "insert into pms2_team_member(tnm,mid) values(?,?)");
                stmt4.setString(1, teamName);
                stmt4.setString(2, memberId);
                stmt4.executeUpdate();
                out.println("<p>팀에 회원을 추가하였습니다.</p>");
            }
        } catch (Exception e) {
            out.printf("<p>%s</p>\n", e.getMessage());
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
}
