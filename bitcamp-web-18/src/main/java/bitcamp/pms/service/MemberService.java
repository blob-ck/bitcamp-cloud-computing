package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Service
public class MemberService {

	@Autowired MemberDao memberDao;

	public List<Member> list(int page, int size) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("startIndex", (page -1 ) * size); //인덱스는 0부터 시작하므로/ 시작 인덱스를 구한다
		params.put("pageSize", size); //페이지 당 보여줄 행의 갯수
		return memberDao.selectList(params);
	}

	public int insert(Member member) {
		return memberDao.insert(member);
	}

	public int delete(String id) {
		return memberDao.delete(id);
	}
	
	
	//메서드에 @Transactional을 붙이면,
	// 메서드에서 수행하는 작업들을 한 단위로 묶어서 다루겠다는 의미다.
	// 따라서 작업들 중 어느 하나라도 실패하면,
	// 이전에 성공했던 모든 작업들이 취소된다.
	// 메서드 호출이 모두 예외없이 정상적으로 끝나면
	// DBMS에 commit을 요청하여 지금까지 한 작업을 실제 테이블에 적용한다.
	//@Transactional()
	public int update(Member member) {
		int count = memberDao.update(member);
		
		if(count != 100) {
			throw new RuntimeException("일부러 에러 발생");
		}
		
		
		return count;
	}

	public Member selectOne(String id) {
		return memberDao.selectOne(id);
	}
	
	public int getTotalPage(int size) {
		int count = memberDao.countAll(size);
		int totalPage = count / size;
		if(count % size != 0) {
			totalPage++;
		}
		return totalPage;
	}
}
