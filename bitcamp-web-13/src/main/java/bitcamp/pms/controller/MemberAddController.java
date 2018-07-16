package bitcamp.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller
public class MemberAddController {
	
	MemberDao memberDao;
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberAddController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberAddController() {
		super();
	}

	@RequestMapping("/member/add")
	public String service(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//GET POST 구별을 하지 못하므로, 요청정보에서 GET인지 판별하고 맞다면 입력폼으로 리턴한다.
		
		if (request.getMethod().equals("GET")) {
			return "/member/form.jsp";
		}
		
		Member member = new Member();
		member.setId(request.getParameter("id"));
		member.setEmail(request.getParameter("email"));
		member.setPassword(request.getParameter("password"));
		
		memberDao.insert(member);
		
		return "redirect:list";
	}
}
