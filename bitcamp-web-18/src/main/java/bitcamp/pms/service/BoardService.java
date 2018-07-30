// 업무로직 구현체 - 고객사 마다 다른 구현을 할 수 있다.
package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;

@Service
public class BoardService {
    
    BoardDao boardDao;
    
    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
    
    public List<Board> list(int pageNo, int pageSize) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("startRowNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        
        return boardDao.selectList(params);
    }
    
    public Board get(int no) {
        return boardDao.selectOne(no);
    }
    
    public int add(Board board) {
        return boardDao.insert(board);
    }
    
    public int update(Board board) {
        return boardDao.update(board);
    }
    
    public int delete(int no) {
        return boardDao.delete(no);
    }
}

//ver 53 - 클래스 추가






