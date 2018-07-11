package bitcamp.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

public class MemberUpdateController implements PageController {
	
	MemberDao memberDao;
	
	public MemberUpdateController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public String service(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Member member = new Member();
		member.setEmail(request.getParameter("email"));
		member.setPassword(request.getParameter("password"));
		member.setId(request.getParameter("id"));
		
		if (memberDao.update(member) == 0) {
			return "/member/updatefail.jsp";
		} else {
			return "redirect:list";
		}
	}
}
