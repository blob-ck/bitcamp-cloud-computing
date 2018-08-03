package bitcamp.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//파일업로드 방법1 - Apache common 방법1
@WebServlet("/fileupload01")
public class FileUpaloadServlet01 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 일반 폼으로 전송된 한글 데이터가 유니코드로 바뀔 때 깨지지 않게 하려면
        //getRequest() 하기 전에 request.setCharacterEncoding("UTF-8") 를 사용해 왔으나
        // multipart/data-form 으로 받아온 요청은 인코딩을 사용할 수 없다.
        // 파일 업로드가 아닌 일반 문자요청만 처리할 수 있다.
        
        //업로드 파일을 저장하는 역할
        // factory를 사용하는 이유는 클라이언트에서 넘어온 데이터 중
        // 텍스트와 파일을 나누는 객체가 필요한데 분리해 놓으면 아마존이든 로컬이든
        // 서버마다 다른 객체를 유연하게 바꿔 사용할 수 있기 때문이다.
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //저장할 임시폴더
        // Configure a repository (to ensure a secure temp location is used)
        /*ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);*/

        // multipart 데이터를 파싱한 후
        // 위에서 설정한 factory를 이용해서 데이터를 저장한다.
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        
        
        try {
            // 클라이언트가 보낸 데이터를 분석한다.
            // Parse the request
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    //일반 문자
                    paramMap.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    //파일 명을 중복되지 않게 하도록 UUDI를 사용한다.
                    String newfilename = UUID.randomUUID().toString();
                    
                    //webapp/files의 절대경로를 받아온다. 문자열로 전부 경로를 입력해 버리면 서버 변경시 앞의 경로가 바뀔때도 있으므로
                    //상대경로로 절대경로를 얻어온다.
                    String path = req.getServletContext().getRealPath("/files/" + newfilename);
                    //파라미터로 File객체를 받는다.
                    item.write(new File(path));
                    //파일 데이터
                    //paramMap.put(item.getFieldName(), item.getName());
                    paramMap.put(item.getFieldName(), newfilename);
                }
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
        
        //resp.setContentType("text/plain;charset=UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //out.printf("name = %s\n", req.getParameter("name"));
        //out.printf("age = %s\n", req.getParameter("age"));
        //out.printf("photo = %s\n", req.getParameter("photo"));
        out.println("<html><head><title>파일 업로드</title></head><body>");
        out.printf("name = %s<br>\n", paramMap.get("name"));
        out.printf("age = %s<br>\n", paramMap.get("age"));
        out.printf("photo = <a href='files/%s'/>%s</a><br>\n", paramMap.get("photo"), paramMap.get("photo"));
        out.printf("<img src=/files/%s><br>", paramMap.get("photo"));
        out.printf("<img id='img1' src=/files/%s><br>", paramMap.get("photo"));
        out.println("</body></html>");
        
        out.printf("<script>"+
        "setTimeout(() => {document.getElementById('img1').src = 'files/%s';}, 5000);"+
        "</script>", paramMap.get("photo"));
    }
}
