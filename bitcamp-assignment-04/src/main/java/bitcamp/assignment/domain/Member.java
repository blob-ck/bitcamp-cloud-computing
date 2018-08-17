package bitcamp.assignment.domain;

import java.io.Serializable;

public class Member implements Serializable {

    //Serializable을 구현함으로써 자바에서도 객체 직렬화를 허용한다는 의미? 다시 자세히 찾아볼 것!
    //객체 직렬화(바이트배열로 변환하는 과정)에서 버전을 명시함으로써 역직렬화 할 때 오류를 최소화하기 위함.
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected String email;
    protected String password;
    
    @Override
    public String toString() {
        return "Member [name=" + name + ", email=" + email + ", password=" + password + "]";
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
