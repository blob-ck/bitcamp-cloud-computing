package com.bcard.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcard.domain.Member;
import com.bcard.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
    
    //@Autowired MemberDao memberDao;
    @Autowired MemberService memberService;
    
    @PostMapping("register")
    public Object register(Member member) throws Exception {
        System.out.println("Register! "+member);
        HashMap<String, Object> data = new HashMap<String, Object>();
        try {
            memberService.register(member);
            data.put("status", "success");
        }catch(Exception e) {
            data.put("status", "fail");
            System.out.println(e);
        }
        return data;
    }
    
    
    @PostMapping("login")
    public Object login(Member member) throws Exception {
        
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        HashMap<String, Object> data = new HashMap<String, Object>();
        
        member = memberService.login(member);
        if(member == null) {
            data.put("status", "fail");
        } else {
            data.put("email", member.getEmail());
            data.put("status", "success");
        }
        return data;
    }
    
    @PostMapping("list")
    public Object list(String email) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        
        List<Member> list = memberService.list(email);
        data.put("list", list);
        data.put("email", email);
        data.put("status", "success");
        //data.put("totalPage", memberService.getTotalPage(size));
        System.out.println(list);
        return data;
    }
}
