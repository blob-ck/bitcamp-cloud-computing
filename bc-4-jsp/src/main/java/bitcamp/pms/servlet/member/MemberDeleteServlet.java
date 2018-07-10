package bitcamp.pms.servlet.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.MemberDao;

@SuppressWarnings("serial")
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	
    @Override
    protected void doGet(
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
    	
    	/*String teamName = request.getParameter("teamName");
    	String memberId = request.getParameter("memberId");*/
        
        response.setContentType("text/html;charset=UTF-8");
        
        try {
        	
        	//제약조건 어찌 처리할지  ㅈㅈ 나중에 프레임웍까지 전부 쓰면 시도해보기로  - cascade?
        	//이건 설계상 방향을 정하는 것이므로 일단 수업따라가는데 집중
        	/*//team_member 행 삭제
        	TeamMemberDao teamMemberDao = (TeamMemberDao)getServletContext().getAttribute("teamMemberDao");
        	teamMemberDao.deleteById(memberId);
        	System.out.println("teamMemberDao 행삭제 완료");
        	
        	//update mid null 입력해야하는데 입력할땐 null 입력가능이고 막상 테이블 제약조건은 FK라서 null입력 불가다.
        	//그냥 작업자=회원 탈퇴하면 작업도 삭제하기로...
        	TaskDao taskDao = (TaskDao)getServletContext().getAttribute("taskDao");
        	taskDao.update(memberId);*/
        	
        	MemberDao memberDao = (MemberDao)getServletContext().getAttribute("memberDao");
        	memberDao.delete(request.getParameter("id"));
        	
        	response.sendRedirect("list");
        } catch (Exception e) {
        	request.setAttribute("error", e);
        	RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
        	rd.forward(request, response);
        }
    }
}

