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
    
    @PostMapping("memberResign")
    public Object memberResign(Member member) {
    	HashMap<String, Object> data = new HashMap<>();
        if (memberService.memberResign(member) == 1){
            data.put("status", "success");
        } else {
            data.put("status", "fail");
        }
    	return data;
    }
    
    
    @PostMapping("cardSearch")
    public Object cardSearch(Member member) throws Exception {
    	HashMap<String, Object> data = new HashMap<>();
        List<Member> list = memberService.cardSearch(member);
        if (list.isEmpty()) {
            data.put("status", "fail");
        } else {
            data.put("list", list);
            data.put("status", "success");
        }
    	return data;
    }
    
    @PostMapping("cardDelete")
    public Object cardDelete(Member member) {
    	HashMap<String, Object> data = new HashMap<>();
    	try {
    		memberService.cardDelete(member);
    		System.out.println(member);
    		data.put("status", "success");
    	} catch (Exception e) {
    		data.put("status", "fail");
    		System.out.println(e);
    	}
    	return data;
    }
    
    @PostMapping("cardUpdate")
    public Object cardUpdate(Member member) {
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
    public Object register(Member member) {
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
