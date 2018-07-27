package bitcamp.pms.controller.json;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.pms.domain.Member;
import bitcamp.pms.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	//@Autowired MemberDao memberDao;
	@Autowired MemberService memberService;
	
	// list ============================================================================================================
	@RequestMapping("list")
	public Object list(@RequestParam(defaultValue="1") int page, 
						@RequestParam(defaultValue="3") int size
			) throws Exception {
		
		if(page <= 1) {
			page = 1;
		}
		
		if(page < 0 || size > 20) {
			size = 3;
		}
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		//List<Member> list = memberDao.selectList(params);
		List<Member> list = memberService.list(page, size);
		data.put("list", list);
		data.put("page", page);
		data.put("size", size);
		data.put("totalPage", memberService.getTotalPage(size));
		
		return data;
	}
	
	
	//add ==============================================================================================================
	
	@GetMapping("form")
	public void form() {
		
		//리턴값이 void 라면 요청 url을 리턴한다
		//return "member/form";
	}

	@PostMapping("add")
	public String add(Member member) throws Exception {
		
		memberService.insert(member);
		
		return "redirect:list";
	}
	
	
	//delete ===========================================================================================================
	@RequestMapping("delete")
	public String delete(String id) throws Exception {
		memberService.delete(id);
		return "redirect:list";
	}
	
	
	//update ===========================================================================================================
	@RequestMapping("update")
	public String update(Member member) throws Exception {
		
		if (memberService.update(member) == 0) {
			return "member/updatefail";
		} else {
			return "redirect:list";
		}
	}
	
	
	//view =============================================================================================================
	@RequestMapping("view/{id}")
	public Object view(@PathVariable String id) throws Exception {

	    HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("member", memberService.selectOne(id));
		
		return data;
	}
}
