package bitcamp.pms.servlet.board;

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
@WebServlet("/board/view")
public class BoardViewServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int no = Integer.parseInt(request.getParameter("no"));
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>게시물 보기</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>게시물 보기</h1>");
        out.println("<form action='update' method='post'>");
        try {
        	
        	Class.forName("com.mysql.jdbc.Driver");
            try (
                Connection con = DriverManager.getConnection(
                		 "jdbc:mysql://13.209.48.23:3306/studydb",
                         "study", "1111");
                PreparedStatement stmt = con.prepareStatement(
                    "select bno,titl,cont,cdt from pms2_board where bno=?");) {
                
                stmt.setInt(1, no);
                
                try (ResultSet rs = stmt.executeQuery();) {
                    if (!rs.next()) {
                    	out.println("유효하지 않은 게시물 번호입니다.");
                    }else {
                    	
                    out.println("<table border='1'>");
                    out.println("<tr><th>번호</th><td>");
                    out.printf("    <input type='text' name='no' value='%d' readonly></td></tr>\n", 
                    		rs.getInt("bno"));
                    out.println("<tr><th>제목</th>");
                    out.printf("    <td><input type='text' name='title' value='%s'></td></tr>\n",
                    		rs.getString("titl"));
                    out.println("<tr><th>내용</th>");
                    out.printf("    <td><textarea name='content' rows='10' cols='60'>%s</textarea></td></tr>\n",
                    		rs.getString("cont"));
                    out.printf("<tr><th>등록일</th><td>%s</td></tr>\n", 
                    		rs.getDate("cdt"));
                    out.println("</table>");
                    
                    }
                }
            }  
        } catch (Exception e) {
            out.printf("<p>%s</p>\n", e.getMessage());
            e.printStackTrace(out);
        }
        out.println("<p>");
        out.println("<a href='list'>목록</a>");
        out.println("<button>변경</button>");
        out.printf("<a href='delete?no=%d'>삭제</a>\n", no);
        out.println("</p>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
	
	
	}
}
