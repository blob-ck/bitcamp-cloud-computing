package bitcamp.pms.servlet.task;

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

@SuppressWarnings("serial")
@WebServlet("/task/update")
public class TaskUpdateServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
        String teamName = request.getParameter("teamName");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.printf("<meta http-equiv='Refresh' content='1;url=list?teamName=%s'>\n",
                teamName);
        out.println("<title>작업 변경</title>");
        out.println("</head>");
        out.println("<body>");
        out.printf("<h1>'%s' 팀의 작업 변경</h1>\n", teamName);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (
                Connection con = DriverManager.getConnection(
            		"jdbc:mysql://13.209.48.23:3306/studydb",
                    "study", "1111");
                PreparedStatement stmt = con.prepareStatement(
                    "update pms2_task set titl=?,sdt=?,edt=?,mid=?,tnm=? where tano=?");) {
                
                stmt.setString(1, request.getParameter("title"));
                stmt.setDate(2, Date.valueOf(request.getParameter("startDate")), Calendar.getInstance(Locale.KOREAN));
                stmt.setDate(3, Date.valueOf(request.getParameter("endDate")), Calendar.getInstance(Locale.KOREAN));
                stmt.setString(4, request.getParameter("memberId"));
                stmt.setString(5, request.getParameter("teamName"));
                stmt.setInt(6, Integer.parseInt(request.getParameter("no")));
                if (stmt.executeUpdate() == 0) {
                	out.println("<p>해당 작업이 없습니다.</p>");
                } else {
                	out.println("<p>변경하였습니다.</p>");
                }
            }
        } catch (Exception e) {
            out.println("<p>변경 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
}
