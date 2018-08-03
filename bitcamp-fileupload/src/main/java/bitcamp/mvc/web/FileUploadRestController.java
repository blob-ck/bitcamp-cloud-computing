package bitcamp.mvc.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//파일업로드 방법4 - jquery AJAX 로 file upload
@RestController
public class FileUploadRestController {
    
    //getRealPath를 사용하기 위함
    @Autowired ServletContext sc;
    
    @RequestMapping("/json-upload01")
    public Object upload01(String name, int age, MultipartFile photo) {
        //새 파일명 준비
        String newfilename = UUID.randomUUID().toString();
        String path = sc.getRealPath("/files/" + newfilename);
        
        try {
            photo.transferTo(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        HashMap<String, Object> result = new HashMap<>();
        
        result.put("name", name);
        result.put("age", age);
        result.put("newfilename", newfilename);
        
        return result;
    }
    
}
