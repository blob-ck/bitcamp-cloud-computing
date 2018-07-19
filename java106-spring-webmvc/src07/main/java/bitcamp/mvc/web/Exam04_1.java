// @RequestMapping - HTTP 파라미터로 메서드 구분하기
package bitcamp.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
@RequestMapping("/exam04_1") 
public class Exam04_1 {
    
    // 파라미터 값의 존재 여부에 따라 메서드를 구분할 수 있다.
    // 테스트 방법: 
    // 예1) mvc/exam04_1/get?name=aaa
    // 예2) mvc/exam04_1/get?age=20
    // 예3) mvc/exam04_1/get?name=aaa&age=20
    @GetMapping(value="get", params="name")  
    @ResponseBody  
    public String m1() {
        return "Exam04_1.m1()";
    }
    
    @GetMapping(value="get", params="age")  
    @ResponseBody  
    public String m2() {
        return "Exam04_1.m2()";
    }
    
    // params 라는 이름에서 알 수 있듯이 배열변수에 초기화 하려면 중괄호 { } 를 반드시 써줘야 한다. 예외로 값이 한개라면 괄호 생략 가능!
    @GetMapping(value="get", params={"name","age"})  
    @ResponseBody  
    public String m3() {
        return "Exam04_1.m3()";
    }
    
}







