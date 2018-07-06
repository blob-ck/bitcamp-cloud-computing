package bitcamp.pms.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://13.209.48.23:3306/studydb", "study",
					"1111");
					PreparedStatement stmt = con
							.prepareStatement("select dscrt, sdt, edt, max_qty from pms2_team where name=?");
					PreparedStatement stmt1 = con.prepareStatement("select tm.mid, m.email"
							+ "    from pms2_team_member tm inner join pms2_member m on tm.mid=m.mid"
							+ "    where tm.tnm=?");) {
				stmt.setString(1, teamName);
				try (ResultSet rs = stmt.executeQuery()) {
					if (!rs.next()) {
						out.println(teamName + " 팀은 존재하지 않습니다.");
						return;
					}
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

				stmt1.setString(1, teamName);

				try (ResultSet rs = stmt1.executeQuery();) {
					while (rs.next()) {
						out.printf("            <option>%s</option>\n", rs.getString("mid"));
					}
				}
				out.println("        </select>");
				out.println("    </td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("<button>등록</button>");
				out.println("</form>");
			}
		} catch (Exception e) {
			out.printf("<p>%s</p>\n", e.getMessage());
			e.printStackTrace(out);
		}
		out.println("</body>");
		out.println("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		Date startDate = Date.valueOf(request.getParameter("startDate"));
		Date endDate = Date.valueOf(request.getParameter("endDate"));
		String teamName = request.getParameter("teamName");
		String memberId = request.getParameter("memberId");

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.printf("<meta http-equiv='Refresh' content='1;url=list?teamName=%s'>\n", teamName);
		out.println("<title>작업 등록</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>작업 등록 결과</h1>");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://13.209.48.23:3306/studydb", "study",
					"1111");
					PreparedStatement stmt = con
							.prepareStatement("select dscrt, sdt, edt, max_qty from pms2_team where name=?");
					PreparedStatement stmt2 = con
							.prepareStatement("select mid from pms2_team_member where tnm=? and mid=?");
					PreparedStatement stmt3 = con
							.prepareStatement("insert into pms2_task(titl,sdt,edt,mid,tnm) values(?,?,?,?,?)");) {

				stmt.setString(1, teamName);

				try (ResultSet rs = stmt.executeQuery();) {
					if (!rs.next()) {
						out.println(teamName + " 팀은 존재하지 않습니다.");
						return;
					}

					boolean isExist = false;
					stmt2.setString(1, teamName);
					stmt2.setString(2, memberId);
					try (ResultSet rs2 = stmt2.executeQuery()) {
						if (rs2.next()) {
							isExist = true;
						}
						isExist = false;
					}

					if (memberId.length() > 0 && isExist) {
						out.println(memberId + "는 이 팀의 회원이 아닙니다.");
						return;
					}

					stmt3.setString(1, title);
					stmt3.setDate(2, startDate, Calendar.getInstance(Locale.KOREAN));
					stmt3.setDate(3, endDate, Calendar.getInstance(Locale.KOREAN));
					stmt3.setString(4, memberId);
					stmt3.setString(5, teamName);
					stmt3.executeUpdate();
					out.println("<p>등록 성공!</p>");
				}
			}
		} catch (Exception e) {
			out.printf("<p>%s</p>\n", e.getMessage());
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}
}
