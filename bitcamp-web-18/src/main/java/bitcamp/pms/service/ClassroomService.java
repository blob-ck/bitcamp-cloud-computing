// 업무로직 구현체 - 고객사 마다 다른 구현을 할 수 있다.
package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import bitcamp.pms.dao.ClassroomDao;
import bitcamp.pms.domain.Classroom;

@Service
public class ClassroomService {
    
    ClassroomDao classroomDao;
    
    public ClassroomService(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }
    
    
    public List<Classroom> list(int pageNo, int pageSize) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("startRowNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        
        return classroomDao.selectList(params);
    }
    
    
    public Classroom get(int no) {
        return classroomDao.selectOne(no);
    }
    
    
    public int add(Classroom classroom) {
        return classroomDao.insert(classroom);
    }
    
    
    public int update(Classroom classroom) {
        return classroomDao.update(classroom);
    }
    
    
    public int delete(int no) {
        return classroomDao.delete(no);
    }
}

//ver 53 - 클래스 추가






