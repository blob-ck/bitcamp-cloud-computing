package com.bcard.dao;

import java.util.List;

import com.bcard.domain.Member;

public interface MemberDao {

    Member login(String email, String password);

    Member login(Member member);

    List<Member> list(String email);

    void register(Member member);

}
