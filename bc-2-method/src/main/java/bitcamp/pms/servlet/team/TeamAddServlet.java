package bitcamp.pms.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.domain.Team;

@SuppressWarnings("serial")
@WebServlet("/team/add")
public class TeamAddServlet extends HttpServlet {

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
        
        out.println("<title>팀 등록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>팀 등록 결과</h1>");
        
        try {
        	insert(team);
            out.println("<p>등록 성공!</p>");
        } catch (Exception e) {
            out.println("<p>등록 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
	
	
	
	private int insert(Team team) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
        try (
            Connection con = DriverManager.getConnection(
        		"jdbc:mysql://13.209.48.23:3306/studydb",
                "study", "1111");
            PreparedStatement stmt = con.prepareStatement(
                "insert into pms2_team(name,dscrt,max_qty,sdt,edt) values(?,?,?,?,?)");) {
            
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getDescription());
            stmt.setInt(3, team.getMaxQty());
            stmt.setDate(4, team.getStartDate());
            stmt.setDate(5, team.getEndDate());

            return stmt.executeUpdate();
        }
	}
}
