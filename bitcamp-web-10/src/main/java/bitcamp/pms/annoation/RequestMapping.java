package bitcamp.pms.annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	
	//모양?? 메소?��?���?�?, ?��로퍼?��명이�?�? getValue()�? ?��?��?�� value()�? ?��?��
	String value() default "";
}
