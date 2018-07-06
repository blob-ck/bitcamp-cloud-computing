package bitcamp.pms.servlet.task;

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
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://13.209.48.23:3306/studydb", "study",
					"1111");
					PreparedStatement stmt = con
							.prepareStatement("select dscrt, sdt, edt, max_qty from pms2_team where name=?");) {
				stmt.setString(1, teamName);
				try (ResultSet rs = stmt.executeQuery()) {

					if (!rs.next()) {
						out.println(teamName + " 팀은 존재하지 않습니다.");
						return;
					}

					out.printf("<p><a href='add?teamName=%s'>새작업</a></p>\n", teamName);
					out.println("<table border='1'>");
					out.println("<tr>");
					out.println("    <th>번호</th><th>작업명</th><th>기간</th><th>작업자</th>");
					out.println("</tr>");
				}
			}

			try (Connection con = DriverManager.getConnection("jdbc:mysql://13.209.48.23:3306/studydb", "study",
					"1111");
					PreparedStatement stmt2 = con
							.prepareStatement("select tano,titl,sdt,edt,stat,mid from pms2_task where tnm=?");) {
				stmt2.setString(1, teamName);
				try (ResultSet rs2 = stmt2.executeQuery()) {

					while (rs2.next()) {
						out.println("<tr>");
						out.printf("    <td>%d</td>", rs2.getInt("tano"));
						out.printf("    <td><a href='view?no=%d'>%s</a></td>", rs2.getInt("tano"),
								rs2.getString("titl"));
						out.printf("    <td>%s ~ %s</td>", rs2.getDate("sdt"), rs2.getDate("edt"));
						out.printf("    <td>%s</td>\n", (rs2.getString("mid") == null) ? "-" : rs2.getString("mid"));
						out.println("</tr>");
					}
					out.println("</table>");
				}
			}
		} catch (Exception e) {
			out.printf("<p>%s</p>\n", e.getMessage());
			e.printStackTrace(out);
		}
		out.println("</body>");
		out.println("</html>");
	}
}
