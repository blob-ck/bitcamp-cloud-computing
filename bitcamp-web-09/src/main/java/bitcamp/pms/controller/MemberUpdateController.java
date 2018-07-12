package bitcamp.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.annoation.Autowired;
import bitcamp.pms.annoation.Controller;
import bitcamp.pms.annoation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller("/member/update")
public class MemberUpdateController {
	
	MemberDao memberDao;
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberUpdateController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public MemberUpdateController() {
	}

	@RequestMapping
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
