package bitcamp.pms.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.TaskDao;
import bitcamp.pms.dao.TeamDao;
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
        	TeamDao teamDao = (TeamDao)getServletContext().getAttribute("teamDao");
			Team team = teamDao.selectOne(teamName);
			if (team == null) {
				out.println(teamName + " 팀은 존재하지 않습니다.");
				return;
			}

			out.printf("<p><a href='add?teamName=%s'>새작업</a></p>\n", teamName);
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("    <th>번호</th><th>작업명</th><th>기간</th><th>작업자</th>");
			out.println("</tr>");
			
			TaskDao taskDao = (TaskDao)getServletContext().getAttribute("taskDao");
			ArrayList<Task> list = taskDao.selectList(team.getName());

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
}
