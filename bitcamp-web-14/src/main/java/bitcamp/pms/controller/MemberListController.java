package bitcamp.pms.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller
public class MemberListController {
	
	MemberDao memberDao;

	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberListController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberListController() {
	}

	@RequestMapping("/member/list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> params = new HashMap<>();
		if(request.getParameter("page") != null && request.getParameter("size") != null) {
			int page = Integer.parseInt(request.getParameter("page"));
			int size = Integer.parseInt(request.getParameter("size"));
			params.put("startIndex", (page -1 ) * size); //인덱스는 0부터 시작하므로/ 시작 인덱스를 구한다
			params.put("pageSize", size); //페이지 당 보여줄 행의 갯수
		}
		
        try {
        	//MemberDao memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
    		List<Member> list = memberDao.selectList(params);
    		request.setAttribute("list", list);
    		
    		//request.setAttribute("view", "/member/list.jsp");
    		return "member/list";
    		
        } catch (Exception e) {
        	request.setAttribute("error", e);
        	throw e;
        }
	}
}
