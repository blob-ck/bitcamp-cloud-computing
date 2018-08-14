package bitcamp.uml.classdiagram.ex3;

//Composition (합성)
//한 객체가 다른 객체를 포함하는 관계
//=> Container 와 Containee가 같은 생명주기를 가진다.
//=> 즉, Container가 소멸될 때 Containee도 소멸된다.
public class Member {

    // 합성 관계에서는 보통 Containee를 Container에서 생성한다.
    Address juso;
    
    public Member() {
        juso = new Address("1111222", "기본주소", "상세주소");
    }
}
