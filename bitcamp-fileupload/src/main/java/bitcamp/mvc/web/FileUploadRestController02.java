package bitcamp.mvc.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//파일업로드 방법4 - jquery AJAX 로 file upload
@RestController
@RequestMapping("/ajax")
public class FileUploadRestController02 {
    
    //getRealPath를 사용하기 위함
    @Autowired ServletContext sc;
    
    @RequestMapping("upload01")
    public Object upload01(String name, String age, MultipartFile[] files) {
        
        System.out.println("FileUploadRestController02 호출됨");
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        
        ArrayList<String> filenames = new ArrayList<>();
        result.put("filenames", filenames); //여기에 리스트의 주소를 담았으므로, 아래에서 값을 리스트에 추가해도 된다.
        
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;
                //새 파일명 준비
                String newfilename = UUID.randomUUID().toString();
                String path = sc.getRealPath("/files/" + newfilename);
                file.transferTo(new File(path));
                filenames.add(newfilename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
