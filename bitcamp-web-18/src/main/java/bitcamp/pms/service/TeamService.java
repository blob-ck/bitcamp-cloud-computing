// 업무로직 구현체 - 고객사 마다 다른 구현을 할 수 있다.
package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import bitcamp.pms.dao.TaskDao;
import bitcamp.pms.dao.TeamDao;
import bitcamp.pms.dao.TeamMemberDao;
import bitcamp.pms.domain.Member;
import bitcamp.pms.domain.Team;

@Service
public class TeamService {
    
    TeamDao teamDao;
    TeamMemberDao teamMemberDao;
    TaskDao taskDao;
    
    public TeamService(
            TeamDao teamDao, 
            TeamMemberDao teamMemberDao,
            TaskDao taskDao) {
        this.teamDao = teamDao;
        this.teamMemberDao = teamMemberDao;
        this.taskDao = taskDao;
    }
    
    public List<Team> list(int pageNo, int pageSize) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("startRowNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        
        return teamDao.selectList(params);
    }
    
    public Team get(String name) {
        return teamDao.selectOne(name);
    }
    
    public Team getWithMembers(String name) {
        return teamDao.selectOneWithMembers(name);
    }
    
    public int add(Team member) {
        return teamDao.insert(member);
    }
    
    public int update(Team member) {
        return teamDao.update(member);
    }
    
    public int delete(String name) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("teamName", name);
        
        teamMemberDao.delete(params);
        taskDao.deleteByTeam(name);
        
        // 팀 회원과 팀 작업을 삭제한 다음에 예외가 발생한다면 
        // 이전에 삭제한 작업은 취소(rollback)되어야 한다.
        // 트랜잭션을 사용하지 않는다면 auto commit이기 때문에 롤백되지 않는다.
        // 테스트를 하려면 다음 문장을 주석 풀고 팀을 삭제해봐라!
        //int result = 100 / 0;
        
        return teamDao.delete(name);
    }
    
    public boolean isMember(String teamName, String memberId) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("teamName", teamName);
        params.put("memberId", memberId);
        
        return teamMemberDao.isExist(params);
    }
    
    public int addMember(String teamName, String memberId) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("teamName", teamName);
        params.put("memberId", memberId);
        
        return teamMemberDao.insert(params);
    }
    
    public int deleteMember(String teamName, String memberId) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("teamName", teamName);
        params.put("memberId", memberId);
        
        return teamMemberDao.delete(params);
    }
    
    public List<Member> getMembersWithEmail(String teamName) {
        return teamMemberDao.selectListWithEmail(teamName);
    }
}

//ver 53 - 클래스 추가






