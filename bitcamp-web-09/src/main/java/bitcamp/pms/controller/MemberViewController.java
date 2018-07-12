package bitcamp.pms.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.annoation.Autowired;
import bitcamp.pms.annoation.Controller;
import bitcamp.pms.annoation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller("/member/view")
public class MemberViewController {
	
	MemberDao memberDao;
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public MemberViewController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public MemberViewController() {
	}

	@RequestMapping
	public String view(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		response.setContentType("text/html;charset=UTF-8");

		Member member = memberDao.selectOne(id);
		request.setAttribute("member", member);
		
		return "/member/view.jsp";
	}
}
