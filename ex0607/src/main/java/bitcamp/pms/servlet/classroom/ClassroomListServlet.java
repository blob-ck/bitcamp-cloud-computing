package bitcamp.pms.servlet.classroom;

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
@WebServlet("/classroom/list")
public class ClassroomListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>강의 목록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>강의 목록</h1>");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (
                Connection con = DriverManager.getConnection(
            		"jdbc:mysql://13.209.48.23:3306/studydb",
                    "study", "1111");
                PreparedStatement stmt = con.prepareStatement(
                    "select crno,titl,sdt,edt,room from pms2_classroom");
                ResultSet rs = stmt.executeQuery();) {
                
                out.println("<p><a href='form.html'>새 강의</a></p>");
                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("    <th>번호</th><th>강의명</th><th>기간</th><th>강의실</th>");
                out.println("</tr>");
                
                while (rs.next()) {
                    out.println("<tr>");
                    out.printf("    <td>%d</td>\n", rs.getInt("crno"));
                    out.printf("    <td><a href='view?no=%d'>%s</a></td>\n", 
                    		rs.getInt("crno"), rs.getString("titl"));
                    out.printf("    <td>%s~%s</td>\n",
                    		rs.getDate("sdt"), rs.getDate("edt"));
                    out.printf("    <td>%s</td>\n", rs.getString("room"));
                    out.println("</tr>");
                }
            }
            out.println("</table>");
        } catch (Exception e) {
            out.println("<p>목록 가져오기 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
	}
}
