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
@WebServlet("/task/view")
public class TaskViewServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int no = Integer.parseInt(request.getParameter("no"));
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>작업 보기</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>작업 보기</h1>");

		try {
			Task task = selectOne(no);
			if (task == null) {
				out.println("해당 작업을 찾을 수 없습니다.");
				return;
			}


			ArrayList<Member> members = selectListWithEmail(task.getTeam().getName());

			out.println("<form action='update' method='post'>");
			out.printf("<input type='hidden' name='no' value='%d'>\n", no);
			out.println("<table border='1'>");
			out.println("<tr>");
			out.printf(
					"    <th>팀명</th>"
							+ "<td><input type='text' name='teamName' value='%s' readOnly></td>\n",
							task.getTeam().getName());
			out.println("</tr>");
			out.println("<tr>");
			out.printf("    <th>작업명</th>" + "<td><input type='text' name='title' value='%s'></td>\n",
					task.getTitle());
			out.println("</tr>");
			out.println("<tr>");
			out.printf("    <th>시작일</th>" + "<td><input type='date' name='startDate' value='%s'></td>",
					task.getStartDate());
			out.println("</tr>");
			out.println("<tr>");
			out.printf("    <th>종료일</th>" + "<td><input type='date' name='endDate' value='%s'></td>",
					task.getEndDate());
			out.println("</tr>");
			out.println("<tr>");
			out.println("    <th>작업자</th>");
			out.println("    <td>");
			out.println("        <select name='memberId'>");
			out.println("            <option value=''>--선택 안함--</option>");

			for (Member member : members) {
				out.printf("            <option %s>%s</option>\n",
						(member.equals(task.getWorker())) ? "selected" : "",
						member.getId());
			}

			out.println("        </select>");
			out.println("    </td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("    <th>작업상태</th><td><select name='state'>");
			out.printf("        <option value='0' %s>작업대기</option>\n",
					(task.getState() == 0) ? "selected" : "");
			out.printf("        <option value='1' %s>작업중</option>\n",
					(task.getState() == 1) ? "selected" : "");
			out.printf("        <option value='9' %s>작업완료</option>\n",
					(task.getState() == 9) ? "selected" : "");
			out.println("    </select></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<button>변경</button> ");
			out.printf("<a href='delete?no=%d&teamName=%s'>삭제</a>\n",
					no, task.getTeam().getName());
			out.println("</form>");
		} catch (Exception e) {
			out.printf("<p>%s</p>\n", e.getMessage());
			e.printStackTrace(out);
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	
	
	
	
	private Task selectOne(int no) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (
			Connection con = DriverManager.getConnection(
				"jdbc:mysql://13.209.48.23:3306/studydb", 
				"study", "1111");
			PreparedStatement stmt = con.prepareStatement(
				"select titl,sdt,edt,stat,mid,tnm from pms2_task where tano=?");) {

			stmt.setInt(1, no);

			try (ResultSet rs = stmt.executeQuery();) {
				if (!rs.next()) {
					return null;
				}
				Task task = new Task();
				task.setTitle(rs.getString("titl"));
				task.setStartDate(rs.getDate("sdt"));
				task.setEndDate(rs.getDate("edt"));
				task.setState(rs.getInt("stat"));
				
				Member obj1 = new Member();
				obj1.setId(rs.getString("mid"));
				task.setWorker(obj1);
				
				Team obj2 = new Team();
				obj2.setName(rs.getString("tnm"));
				task.setTeam(obj2);
				
				return task;
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
				"select tm.mid, m.email" + 
				" from pms2_team_member tm inner join pms2_member m on tm.mid=m.mid" + 
				" where tm.tnm=?");) {

			stmt.setString(1, teamName);
			try (ResultSet rs = stmt.executeQuery()) {
				
				ArrayList<Member> members = new ArrayList<>();
				
				while(rs.next()) {
					Member member = new Member();
					member.setId(rs.getString("mid"));
					member.setEmail(rs.getString("email"));
					
					members.add(member);
				}
				return members;
			}
		}
	}
}

