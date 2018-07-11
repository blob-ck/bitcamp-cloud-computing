package bitcamp.pms.servlet.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {
	
	@Override
    protected void doPost(
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        // 클라이언트가 보낸 데이터가 어떤 문자표를 사용해서 작성한지 알아야만 
        // String 객체(UTF-16)로 값을 꺼낼 수 있다. 
        request.setCharacterEncoding("UTF-8");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        
        // 지정된 시간이 경과하면 특정 서블릿을 요청하도록 태그를 삽입!
        // => 웹브라우저는 meta 태그의 내용대로 동작한다.
        //    content='경과시간(초);url=요청할URL'
        //
        out.println("<meta http-equiv='Refresh' content='1;url=list'>");
        
        out.println("<title>게시물 등록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>게시물 등록 결과</h1>");
        try {
        	
        	 Class.forName("com.mysql.jdbc.Driver");
             try (
        		 Connection con = DriverManager.getConnection(
                         "jdbc:mysql://13.209.48.23:3306/studydb",
                         "study", "1111");
                 PreparedStatement stmt = con.prepareStatement(
                     "insert into pms2_board(titl,cont,cdt) values(?,?,now())");) {
                 
                 stmt.setString(1, request.getParameter("title"));
                 stmt.setString(2, request.getParameter("content"));
                 stmt.executeUpdate();
        	
             	}
            out.println("<p>등록 성공!</p>");
        } catch (Exception e) {
            out.println("<p>등록 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
    }
}
