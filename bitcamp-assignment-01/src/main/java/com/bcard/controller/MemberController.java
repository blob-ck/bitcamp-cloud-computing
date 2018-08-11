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
    
    @PostMapping("cardUpdate")
    public Object cardUpdate(Member member) throws Exception {
        HashMap<String, Object> data = new HashMap<>();
        try {
            memberService.cardUpdate(member);
            System.out.println(member);
            data.put("status", "success");
        } catch (Exception e) {
            data.put("status", "fail");
            System.out.println(e);
        }
        return data;
    }
    
    
    @PostMapping("cardAdd")
    public Object cardAdd(Member member) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            memberService.cardAdd(member);
            data.put("status", "success");
        } catch (Exception e) {
            data.put("status", "fail");
            System.out.println(e);
        }
        return data;
    }
    
    @PostMapping("register")
    public Object register(Member member) throws Exception {
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
    public Object list(String email) throws Exception {
        HashMap<String, Object> data = new HashMap<String, Object>();
        List<Member> list = memberService.list(email);
        if (list.isEmpty()) {
            data.put("status", "fail");
        } else {
            data.put("list", list);
            data.put("email", email);
            data.put("status", "success");
        }
        System.out.println(list);
        return data;
    }
    
    @PostMapping("view")
    public Object view(String name, Member member) throws Exception {
        HashMap<String, Object> data = new HashMap<String, Object>();
        member = memberService.view(name);
        if (member == null) {
            data.put("status", "fail");
        } else {
            data.put("member", member);
            data.put("status", "success");
        }
        return data;
    }
}
