// 요청 핸들러의 리턴 값 - 콘텐트를 직접 리턴하기 II
package bitcamp.mvc.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/exam06_2") 
public class Exam06_2 {
    
    // request handler에서 콘텐트를 직접 리턴할 때
    // HttpEntity에 담아서 리턴할 수 있다.
	/*통신 메시지 관련 header와 body의 값들을 하나의 객체로 저장하는 것이 HttpEntity 클래스 객체
	Request 부분일 경우 HttpEntity를 상속받은 RequestEntity가, Response 부분일 경우 HttpEntity를 상속받은 ResponseEntity가 있다.
	@ResponseBody나 ResponseEntity를 return 하는거나 결과적으로는 같은 기능이지만
	header 값을 변경시켜야 할 경우엔 @ResponseBody의 경우 파라미터로 Response 객체를 받아서 이 객체에서  header를 변경시켜야 하고,
	ResponseEntity에서는 이 클래스 객체를 생성한뒤 객체에서 header 값을 변경해야 한다.*/
    @GetMapping(value="m1")  
    public HttpEntity<String> m1() {
        // 마찬가지로 응답 콘텐트의 기본 타입은 
        // "text/html;charset=ISO-8859-1" 이다.
        HttpEntity<String> entity = new HttpEntity<>(
                    "Exam06_2.m1() ==> 012ABCabc#!@가각간");
        return entity;
    }
    
    @GetMapping(value="m2")  
    public HttpEntity<String> m2() {
        // 응답 콘텐트의 타입과 문자표를 설정하고 싶다면
        // 응답 헤더를 명시적으로 지정하라!
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        
        HttpEntity<String> entity = new HttpEntity<>(
                    "Exam06_2.m2() ==> 012ABCabc#!@가각간",
                    headers);
        return entity;
    }
    
    @GetMapping(value="m3")  
    public ResponseEntity<String> m3() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        
        // ResponseEntity는 HttpEntity의 하위 클래스이다.
        // 응답할 때 상태코드를 설정할 수 있다.
        ResponseEntity<String> entity = new ResponseEntity<>(
                    "Exam06_2.m3() ==> 012ABCabc#!@가각간",
                    headers,
                    HttpStatus.OK);
        return entity;
    }
}







