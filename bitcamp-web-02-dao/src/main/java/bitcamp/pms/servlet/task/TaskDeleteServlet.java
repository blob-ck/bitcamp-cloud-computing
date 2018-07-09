package bitcamp.pms.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.TaskDao;

@SuppressWarnings("serial")
@WebServlet("/task/delete")
public class TaskDeleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		int no = Integer.parseInt(request.getParameter("no"));
		String teamName = request.getParameter("teamName");

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.printf("<meta http-equiv='Refresh' content='1;url=list?teamName=%s'>\n", teamName);
		out.println("<title>작업 삭제</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>작업 삭제 결과</h1>");

		try {
        	TaskDao taskDao = (TaskDao)getServletContext().getAttribute("taskDao");
			int count = taskDao.delete(no);
			if (count == 0) {
				out.println("<p>해당 작업이 존재하지 않습니다.</p>");
			} else {
				out.println("<p>삭제하였습니다.</p>");
			}
		} catch (Exception e) {
			out.println("<p>삭제 실패!<br>");
			out.println("잠시 후 다시 시도해주세요. 계속 오류 발생 시<br>");
			out.println("담당자(내선: 120)에게 연락주세요.</p>");
			e.printStackTrace(out);
		}
		out.println("</body>");
		out.println("</html>");
	}
}
