package bitcamp.pms.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired MemberDao memberDao;
	
	//@Autowired 없고, setMemberDao() 도 없지만 생성자에  파라미터로 쓰면 자동으로 찾아서 넣어준다.
	//다만 위의  MemberDao필드에 @Autowired를 붙여서 확인해준다. -> 관습?
	public MemberController(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@RequestMapping("list")
	public String list(@RequestParam(defaultValue="1") int page, 
						@RequestParam(defaultValue="3") int size,
						Model model
			) throws Exception {
		
		if(page <= 1) {
			page = 1;
		}
		
		if(page < 0 || size > 20) {
			size = 3;
		}
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("startIndex", (page -1 ) * size); //인덱스는 0부터 시작하므로/ 시작 인덱스를 구한다
		params.put("pageSize", size); //페이지 당 보여줄 행의 갯수
		
    	//MemberDao memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
		List<Member> list = memberDao.selectList(params);
		model.addAttribute("list", list);
		
		//request.setAttribute("view", "/member/list.jsp");
		return "member/list";
	}
	
	
	//add
	
	@GetMapping("form")
	public void form() {
		
		//리턴값이 void 라면 요청 url을 리턴한다
		//return "member/form";
	}

	@PostMapping("add")
	public String add(Member member) throws Exception {
		
		memberDao.insert(member);
		
		return "redirect:list";
	}
	
	
	//delete
	@RequestMapping("delete")
	public String delete(String id) throws Exception {
		memberDao.delete(id);
		return "redirect:list";
	}
	
	
	//update
	@RequestMapping("update")
	public String update(Member member) throws Exception {
		
		if (memberDao.update(member) == 0) {
			return "member/updatefail";
		} else {
			return "redirect:list";
		}
	}
	
	
	//view
	@RequestMapping("view/{id}")
	public String view(@PathVariable String id, Model model) throws Exception {

		Member member = memberDao.selectOne(id);
		model.addAttribute("member", member);
		
		return "member/view";
	}
}
