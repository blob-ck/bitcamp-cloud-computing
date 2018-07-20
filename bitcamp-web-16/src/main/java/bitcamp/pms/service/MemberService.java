package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public int update(Member member) {
		return memberDao.update(member);
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
