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

import net.coobird.thumbnailator.Thumbnails;

//파일업로드 방법4 - jquery AJAX 로 file upload
@RestController
@RequestMapping("/ajax")
public class FileUploadRestController02 {
    
    //getRealPath를 사용하기 위함
    @Autowired ServletContext sc;
    
    @RequestMapping("upload01")
    public Object upload01(String name, String age, MultipartFile[] files) {
        
        System.out.println("upload01 (Rest) 호출됨");
        
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
    
    
    @RequestMapping("upload02")
    public Object upload02(String name, String age, MultipartFile[] files) {
        
        System.out.println("upload02 (Rest) 호출됨");
        
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
                
                // 설명서 : https://github.com/coobird/thumbnailator
                /*Thumbnails.of(new File("path/to/directory").listFiles())
                .size(640, 480)
                .outputFormat("jpg")
                .toFiles(Rename.PREFIX_DOT_THUMBNAIL);*/
                
                //이 방식의 단점은 확장자를 알아서 붙여주므로, 파일명만으로 저장했던 방식으로 사용하면 이미지를 읽어오지 못한다.
                //그러므로 확장자를 여기서 확정지어버린다.
                //view에서 꺼내 쓸 때도 확장자 붙여줘야 한다.
                Thumbnails.of(path)
                .size(50, 50)
                .outputFormat("jpg")
                .toFile(path + "_20X20");
                
                Thumbnails.of(path)
                .size(80, 80)
                .outputFormat("jpg")
                .toFile(path + "_80X80");
                
                Thumbnails.of(path)
                .size(120, 120)
                .outputFormat("jpg")
                .toFile(path + "_120X120");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
