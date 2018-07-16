package bitcamp.pms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.pms.domain.Member;

public interface MemberDao {

	List<Member> selectList(Map<String, Object> params);
	Member selectOne(String id);
	int update(Member member);
	int delete(String id);
	int insert(Member member);
}