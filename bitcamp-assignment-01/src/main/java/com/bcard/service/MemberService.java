package com.bcard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcard.dao.MemberDao;
import com.bcard.domain.Member;

@Service
public class MemberService {
    
    @Autowired MemberDao memberDao;

    public Member login(Member member) {
        return memberDao.login(member);
    }

    public List<Member> list(String email) {
        return memberDao.list(email);
    }

    public void register(Member member) {
        memberDao.register(member);
    }

    public Member view(String name) {
        return memberDao.view(name);
    }

    public void cardAdd(Member member) {
        memberDao.cardAdd(member);
    }

    public void cardUpdate(Member member) {
        memberDao.cardUpdate(member);
    }

	public void cardDelete(Member member) {
		memberDao.cardDelete(member);
	}

	public List<Member> cardSearch(Member member) {
		return memberDao.cardSearch(member);
	}

	public int memberResign(Member member) {
		return memberDao.memberResign(member);
	}

}
