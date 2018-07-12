package bitcamp.pms.annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	
	//모양은 메소드이지만, 프로퍼티명이므로 getValue()가 아니라 value()로 쓴다
	String value() default "";
}
