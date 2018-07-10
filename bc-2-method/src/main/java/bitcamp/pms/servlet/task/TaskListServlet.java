package bitcamp.pms.servlet.task;

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

import bitcamp.pms.domain.Member;
import bitcamp.pms.domain.Task;
import bitcamp.pms.domain.Team;

@SuppressWarnings("serial")
@WebServlet("/task/list")
public class TaskListServlet extends HttpServlet {

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
		out.println("<title>작업 목록</title>");
		out.println("</head>");
		out.println("<body>");
		out.printf("<h1>'%s'의 작업 목록</h1>\n", teamName);

		try {
			Team team = selectOne(teamName);
			if (team == null) {
				out.println(teamName + " 팀은 존재하지 않습니다.");
				return;
			}

			out.printf("<p><a href='add?teamName=%s'>새작업</a></p>\n", teamName);
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("    <th>번호</th><th>작업명</th><th>기간</th><th>작업자</th>");
			out.println("</tr>");
			

			ArrayList<Task> list = selectList(team.getName());

			for (Task task : list) {
				out.println("<tr>");
				out.printf("    <td>%d</td>", task.getNo());
				out.printf("    <td><a href='view?no=%d'>%s</a></td>", task.getNo(),
						task.getTitle());
				out.printf("    <td>%s ~ %s</td>", task.getStartDate(), task.getEndDate());
				out.printf("    <td>%s</td>\n", (task.getWorker() == null) ? "-" : task.getWorker().getId());
				out.println("</tr>");
			}
			out.println("</table>");
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
					"select dscrt, sdt, edt, max_qty from pms2_team where name=?");) {
			stmt.setString(1, teamName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					return null;
				}
				
				Team team = new Team();
				team.setName(teamName);
				team.setDescription(rs.getString("dscrt"));
				team.setStartDate(rs.getDate("sdt"));
				team.setEndDate(rs.getDate("edt"));
				team.setMaxQty(rs.getInt("max_qty"));
				
				return team;
			}
		}
	}
	
	
	private ArrayList<Task> selectList(String teamName)	throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://13.209.48.23:3306/studydb", 
					"study", "1111");
			PreparedStatement stmt = con.prepareStatement(
					"select tano,titl,sdt,edt,stat,mid from pms2_task where tnm=?");) {
			stmt.setString(1, teamName);
			try (ResultSet rs = stmt.executeQuery()) {

				ArrayList<Task> list = new ArrayList<>();
				while (rs.next()) {
					
					Task task = new Task();
					task.setNo(rs.getInt("tano"));
					task.setTitle(rs.getString("titl"));
					task.setStartDate(rs.getDate("sdt"));
					task.setEndDate(rs.getDate("edt"));
					
					//TaskMapper.xml 참조
					Member obj1 = new Member();
					obj1.setId(rs.getString("mid"));
					task.setWorker(obj1);
					
					list.add(task);
				}
				return list;
			}
		}
	}
}
