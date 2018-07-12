package bitcamp.pms.annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//의존객체를 꼽아야하는 setter메소드에는 이 놈을 붙인다. 의존객체는 반드시 필요하므로 꼭  set 메소드 호출해달라는 의미
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
	String value() default "";
}
