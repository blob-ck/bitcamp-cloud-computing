package bitcamp.pms.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.domain.Member;
import bitcamp.pms.domain.Task;
import bitcamp.pms.domain.Team;

@SuppressWarnings("serial")
@WebServlet("/task/add")
public class TaskAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String teamName = request.getParameter("teamName");

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>작업 등록</title>");
		out.println("</head>");
		out.println("<body>");
		out.printf("<h1>'%s' 팀의 작업 등록</h1>\n", teamName);

		try {
			Team team = selectOne(teamName);
			
			if (team == null) {
				out.println(teamName + " 팀은 존재하지 않습니다.");
				return;
			}
			

			out.println("<form action='add' method='post'>");
			out.printf("<input type='hidden' name='teamName' value='%s'>\n", teamName);
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("    <th>작업명</th><td><input type='text' name='title'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("    <th>시작일</th><td><input type='date' name='startDate'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("    <th>종료일</th><td><input type='date' name='endDate'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("    <th>작업자</th>");
			out.println("    <td>");
			out.println("        <select name='memberId'>");
			out.println("            <option value=''>--선택 안함--</option>");

			ArrayList<Member> members = selectListWithEmail(teamName);

			for (Member member : members) {
				out.printf("            <option>%s</option>\n", member.getId());
			}
			
			out.println("        </select>");
			out.println("    </td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<button>등록</button>");
			out.println("</form>");
		} catch (Exception e) {
			out.printf("<p>%s</p>\n", e.getMessage());
			e.printStackTrace(out);
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	private Team selectOne(String teamName) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://13.209.48.23:3306/studydb", 
					"study", "1111");
			PreparedStatement stmt = con.prepareStatement(
					"select dscrt, sdt, edt, max_qty from pms2_team where name=?");
			) {
			stmt.setString(1, teamName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					return null;
				}
				Team team = new Team();
				team.setDescription(rs.getString("dscrt"));
				team.setStartDate(rs.getDate("sdt"));
				team.setEndDate(rs.getDate("edt"));
				team.setMaxQty(rs.getInt("max_qty"));
				
				return team;
			}
		}
	}
	
	
	private ArrayList<Member> selectListWithEmail(String teamName) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://13.209.48.23:3306/studydb", 
					"study", "1111");
			
			PreparedStatement stmt = con.prepareStatement(
					"select tm.mid, m.email"+
					" from pms2_team_member tm inner join pms2_member m on tm.mid=m.mid"+
					" where tm.tnm=?");) {
			
			stmt.setString(1, teamName);
			try (ResultSet rs = stmt.executeQuery();) {
				
				ArrayList<Member> members = new ArrayList<>();
				
				while (rs.next()) {
					Member member = new Member();
					member.setId(rs.getString("mid"));
					member.setEmail(rs.getString("email"));
					
					members.add(member);
				}
				return members;
			}
		}
	}

	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Task task = new Task();
        task.setTitle(request.getParameter("title"));
        task.setStartDate(Date.valueOf(request.getParameter("startDate")));
        task.setEndDate(Date.valueOf(request.getParameter("endDate")));
        task.setTeam(new Team().setName(request.getParameter("teamName")));
        task.setWorker(new Member().setId(request.getParameter("memberId")));

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.printf("<meta http-equiv='Refresh' content='1;url=list?teamName=%s'>\n", task.getTeam().getName());
		out.println("<title>작업 등록</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>작업 등록 결과</h1>");

		try {
			Team team = selectOne(task.getTeam().getName());
			if (team == null) {
				out.println(task.getTeam().getName() + " 팀은 존재하지 않습니다.");
				return;
			}


			if (task.getWorker().getId().length() > 0 && 
					!isExist(task.getTeam().getName(), task.getWorker().getId())) {
				out.println(task.getWorker().getId() + "는 이 팀의 회원이 아닙니다.");
				return;
			}

			insert(task);
			
			out.println("<p>등록 성공!</p>");
		} catch (Exception e) {
			out.printf("<p>%s</p>\n", e.getMessage());
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	
	private boolean isExist(String teamName, String memberId) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (
            Connection con = DriverManager.getConnection(
            		"jdbc:mysql://13.209.48.23:3306/studydb",
					"study", "1111");
        	) {
                
            PreparedStatement stmt = con.prepareStatement(
            		"select mid from pms2_team_member where tnm=? and mid=?");
            stmt.setString(1, teamName);
            stmt.setString(2, memberId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	return true;
                } 
                return false;
            }
        }
	}
	
	
	private int insert(Task task) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://13.209.48.23:3306/studydb", 
					"study", "1111");
			PreparedStatement stmt = con.prepareStatement(
					"insert into pms2_task(titl,sdt,edt,stat,mid,tnm) values(?,?,?,?,?,?)");) {

			stmt.setString(1, task.getTitle());
			stmt.setDate(2, task.getStartDate());
			stmt.setDate(3, task.getEndDate());
			stmt.setInt(4, ((Integer)task.getState()==null) ? 0:task.getState());
			stmt.setString(5, task.getWorker().getId());
			stmt.setString(6, task.getTeam().getName());
			
			return stmt.executeUpdate();
		}
	}
}
