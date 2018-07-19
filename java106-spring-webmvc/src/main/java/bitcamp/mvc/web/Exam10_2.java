// JSON 데이터 출력하기 - Gson 라이브러리로 JSON 데이터 출력하기 
package bitcamp.mvc.web;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bitcamp.mvc.dao.BoardDao;
import bitcamp.mvc.vo.Board;

@Controller 
@RequestMapping("/exam10_2") 
public class Exam10_2 {
    
    BoardDao boardDao;
    
    public Exam10_2(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
    
    @GetMapping(value="list", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String list() throws Exception {
        List<Board> boards = boardDao.selectList();
        
        // Gson 라이브러리를 이용하여 객체를 JSON 문자열로 변환한다.
        //String json = new Gson().toJson(boards);
        
        //Jackson Library를 이용하여 문자열을 변환
        String json = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd")).writeValueAsString(boards);
        System.out.println(json);
        
        // 그리고 그 JSON 문자열을 출력한다.
        return json;
    }
    
    @GetMapping(value="list2")
    @ResponseBody
    public Object list2() {
        List<Board> boards = boardDao.selectList();
        // 요청 핸들러의 리턴 값이 @ResponseBody일 경우,
        // 일반 객체를 리턴하면 
        // 스프링에서 자동으로 JSON으로 바꿔 응답한다.
        
        //스프링에서 자동으로 변환하므로, 위의 list()처럼 list2()내부에서 원하는 Date포맷으로 변환시킬 방법이 없다.
        //그런 이유로  xml설정에서 원하는 포맷을 정하는 수밖에 없다.
        //<mvc:annotation-driven> 에서 설정한다.
        //MappingJackson2HttpMessageConverter 를 설정한다.
        // JSON -> 파라미터타입 / 파라미터타입 -> JSON 양방향으로 변환해준다.(객체타입도 가능)
        return boards;
    }
}







