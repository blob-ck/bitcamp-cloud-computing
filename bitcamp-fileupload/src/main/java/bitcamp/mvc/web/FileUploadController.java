package bitcamp.mvc.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

//파일업로드 방법3 - Spring 으로 file upload
@Controller
public class FileUploadController {
    
    //getRealPath를 사용하기 위함
    @Autowired ServletContext sc;
    
    @RequestMapping("/upload01")
    public void upload01(String name, int age, MultipartFile photo, Model model) {
        //새 파일명 준비
        String newfilename = UUID.randomUUID().toString();
        String path = sc.getRealPath("/files/" + newfilename);
        
        try {
            photo.transferTo(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("newfilename", newfilename);
        
        
    }
    
}
