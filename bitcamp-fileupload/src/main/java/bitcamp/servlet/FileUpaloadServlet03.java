package bitcamp.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//파일업로드 방법1 - Apache common 방법3 Stream ...비추
@WebServlet("/fileupload03")
public class FileUpaloadServlet03 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        InputStream fileContent = null;
        OutputStream fileOut = null;

        try {
            
            //HashMap 대신 ServletFileUpload에 내장된 parseParameterMap을 사용한다. 
            Map<String, List<FileItem>> paramMap = upload.parseParameterMap(req);
            
            //req.getParameterValues("name") ==> 이렇게 같은 이름으로 넘어오는 파라미터를 배열로 받아왔던 것처럼
            String name = paramMap.get("name").get(0).getString("UTF-8");
            String age = paramMap.get("age").get(0).getString();
            FileItem photoItem = paramMap.get("photo").get(0);
            
            String newfilename = UUID.randomUUID().toString();
            String path = req.getServletContext().getRealPath("/files/" + newfilename);
            
            // 이렇게만 해도 깔끔한데...
            //photoItem.write(new File(path));

            // 상황에 따라서 어쩔 수 없이 write 대신 InputStream으로 해야만 한다면.....
            // 입력 스트립을 통해 데이터를 읽고 출력 스트림으로 저장하기
            //----------------------------------------------
            fileContent = photoItem.getInputStream();
            fileOut = new FileOutputStream(path);
            
            byte[] buf = new byte[1024];
            int len = 0;
            
            while ((len = fileContent.read(buf)) != -1) {
                fileOut.write(buf,  0, len);
            }
            
            fileOut.flush();
            //----------------------------------------------
            
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>파일 업로드</title></head><body>");
            out.printf("name = %s<br>\n", name);
            out.printf("age = %s<br>\n", age);
            out.printf("photo = <a href='files/%s'/>%s</a><br>\n", newfilename, newfilename);
            out.printf("<img src=/files/%s><br>", newfilename);
            out.printf("<img id='img1' src=/files/%s><br>", newfilename);
            out.println("</body></html>");
            
            out.printf("<script>"+
                    "setTimeout(() => {document.getElementById('img1').src = 'files/%s';}, 5000);"+
                    "</script>", newfilename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {fileContent.close();} catch (Exception e) {}
            try {fileOut.close();} catch (Exception e) {}
        }
    }
}
