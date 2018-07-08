package bitcamp.pms.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.domain.Member;
import bitcamp.pms.domain.Task;
import bitcamp.pms.domain.Team;

@SuppressWarnings("serial")
@WebServlet("/task/update")
public class TaskUpdateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String teamName = request.getParameter("teamName");

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.printf("<meta http-equiv='Refresh' content='1;url=list?teamName=%s'>\n", teamName);
		out.println("<title>작업 변경</title>");
		out.println("</head>");
		out.println("<body>");
		out.printf("<h1>'%s' 팀의 작업 변경</h1>\n", teamName);

		try {
			
			//setter의 return type을 task로 해서 .set("뭐뭐") 해도 task 이므로 계속해서 .set()을 할 수 있다. 첨봄
			Task task = new Task()
	                .setNo(Integer.parseInt(request.getParameter("no")))
	                .setTitle(request.getParameter("title"))
	                .setStartDate(Date.valueOf(request.getParameter("startDate")))
	                .setEndDate(Date.valueOf(request.getParameter("endDate")))
	                .setState(Integer.parseInt(request.getParameter("state")))
	                .setTeam(new Team().setName(request.getParameter("teamName")))
	                .setWorker(new Member().setId(request.getParameter("memberId")));
			System.out.println(task.getState());
			int count = update(task);
			if (count == 0) {
				out.println("<p>해당 작업이 없습니다.</p>");
			} else {
				out.println("<p>변경하였습니다.</p>");
			}
		} catch (Exception e) {
			out.println("<p>변경 실패!</p>");
			e.printStackTrace(out);
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	
	
	private int update(Task task) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection con = DriverManager.getConnection("jdbc:mysql://13.209.48.23:3306/studydb", "study",
				"1111");
				PreparedStatement stmt = con
						.prepareStatement("update pms2_task set titl=?,sdt=?,edt=?,stat=?,mid=?,tnm=? where tano=?");) {

			stmt.setString(1, task.getTitle());
			stmt.setDate(2, task.getStartDate());
			stmt.setDate(3, task.getEndDate());
			stmt.setInt(4, task.getState());
			stmt.setString(5, task.getWorker().getId());
			stmt.setString(6, task.getTeam().getName());
			stmt.setInt(7, task.getNo());

			return stmt.executeUpdate();
		}
	}
}
