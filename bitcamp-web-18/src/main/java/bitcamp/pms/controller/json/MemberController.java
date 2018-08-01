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
	public Object add(Member member) throws Exception {
	    
	    HashMap<String, Object> data = new HashMap<String, Object>();
	    try {
	        int r = memberService.insert(member);
	        System.out.println(r);
            data.put("status", "success");
	        return data;
        } catch (Exception e) {
            data.put("status", "fail");
            data.put("error", "이미 존재하는 아이디입니다.");
            //e.printStackTrace();
            System.out.println("이미 존재하는 아이디입니다.");
            return data;
        }
	}
	
	
	//delete ===========================================================================================================
	@RequestMapping("delete")
	public Object delete(String id) throws Exception {
	    
	    HashMap<String, Object> result = new HashMap<String, Object>();
	    if (memberService.delete(id) == 0) {
	        result.put("status", "fail");
	        result.put("error", "해당 아이디가 없습니다.");
	    } else {
	        result.put("status", "success");
	    }
	    return result;
	}
	
	
	//update ===========================================================================================================
	@RequestMapping("update")
	public Object update(Member member) throws Exception {
		
	    HashMap<String, Object> result = new HashMap<String, Object>();
		if (memberService.update(member) == 0) {
			result.put("status", "fail");
			result.put("error", "해당 아이디가 없습니다.");
		} else {
			result.put("status", "success");
		}
		return result;
	}
	
	
	//view =============================================================================================================
	@RequestMapping("view/{id}")
	public Object view(@PathVariable String id) throws Exception {

	    HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("member", memberService.selectOne(id));
		
		return data;
	}
}
