package bitcamp.pms.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

public class MemberListController implements PageController {
	
	MemberDao memberDao;
	
	public MemberListController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public String service(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
		HashMap<String, Object> params = new HashMap<>();
		if(request.getParameter("page") != null && request.getParameter("size") != null) {
			int page = Integer.parseInt(request.getParameter("page"));
			int size = Integer.parseInt(request.getParameter("size"));
			params.put("startIndex", (page -1 ) * size); //인덱스는 0부터 시작하므로/ 시작 인덱스를 구한다
			params.put("pageSize", size); //페이지 당 보여줄 행의 갯수
		}
		
		try {
			List<Member> list = memberDao.selectList(params);
			request.setAttribute("list", list);
			
			return "/member/list.jsp";
		} catch (Exception e) {
			request.setAttribute("error", e);
			throw e;
		}
	}
}
