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
@WebServlet("/task/view")
public class TaskViewServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
			int no = Integer.parseInt(request.getParameter("no"));
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://13.209.48.23:3306/studydb", "study",
					"1111");
					PreparedStatement stmt = con
							.prepareStatement("select titl,sdt,edt,stat,mid,tnm from pms2_task where tano=?");) {

				stmt.setInt(1, no);

				try (ResultSet rs = stmt.executeQuery();) {
					if (!rs.next()) {
						out.println("해당 작업을 찾을 수 없습니다.");
						return;
					}

					try (PreparedStatement stmt2 = con
							.prepareStatement("select mid from pms2_team_member where tnm=?");) {

						stmt2.setString(1, rs.getString("tnm"));
						try (ResultSet rs2 = stmt2.executeQuery()) {

							out.println("<form action='update' method='post'>");
							out.printf("<input type='hidden' name='no' value='%d'>\n", no);
							out.println("<table border='1'>");
							out.println("<tr>");
							out.printf(
									"    <th>팀명</th>"
											+ "<td><input type='text' name='teamName' value='%s' readOnly></td>\n",
									rs.getString("tnm"));
							out.println("</tr>");
							out.println("<tr>");
							out.printf("    <th>작업명</th>" + "<td><input type='text' name='title' value='%s'></td>\n",
									rs.getString("titl"));
							out.println("</tr>");
							out.println("<tr>");
							out.printf("    <th>시작일</th>" + "<td><input type='date' name='startDate' value='%s'></td>",
									rs.getString("sdt"));
							out.println("</tr>");
							out.println("<tr>");
							out.printf("    <th>종료일</th>" + "<td><input type='date' name='endDate' value='%s'></td>",
									rs.getString("edt"));
							out.println("</tr>");
							out.println("<tr>");
							out.println("    <th>작업자</th>");
							out.println("    <td>");
							out.println("        <select name='memberId'>");
							out.println("            <option value=''>--선택 안함--</option>");

							while (rs2.next()) {
								out.printf("            <option %s>%s</option>\n",
										(rs.getString("mid").equals(rs2.getString("mid"))) ? "selected" : "",
										rs2.getString("mid"));
							}

							out.println("        </select>");
							out.println("    </td>");
							out.println("</tr>");
							out.println("<tr>");
							out.println("    <th>작업상태</th><td><select name='state'>");
							out.printf("        <option value='0' %s>작업대기</option>\n",
									(rs.getInt("stat") == 0) ? "selected" : "");
							out.printf("        <option value='1' %s>작업중</option>\n",
									(rs.getInt("stat") == 1) ? "selected" : "");
							out.printf("        <option value='9' %s>작업완료</option>\n",
									(rs.getInt("stat") == 9) ? "selected" : "");
							out.println("    </select></td>");
							out.println("</tr>");
							out.println("</table>");
							out.println("<button>변경</button> ");
							out.printf("<a href='delete?no=%d&teamName=%s'>삭제</a>\n", no, rs.getString("tnm"));
							out.println("</form>");
						}
					}
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
