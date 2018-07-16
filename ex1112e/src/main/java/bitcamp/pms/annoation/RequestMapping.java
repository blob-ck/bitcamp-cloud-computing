package bitcamp.pms.annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	
	//ëª¨ì–‘?? ë©”ì†Œ?“œ?´ì§?ë§?, ?”„ë¡œí¼?‹°ëª…ì´ë¯?ë¡? getValue()ê°? ?•„?‹ˆ?¼ value()ë¡? ?“´?‹¤
	String value() default "";
}
