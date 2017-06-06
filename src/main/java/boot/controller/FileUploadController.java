package boot.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by huishen on 16/10/18.
 *
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {
    private Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseEntity fileUpload(@RequestParam("my_file") MultipartFile file, HttpServletRequest request){

        if(!file.isEmpty()){
            try{
                String contextPath = request.getContextPath();
                String servletPath = request.getServletPath();
                ServletContext servletContext = request.getSession().getServletContext();
                System.out.println(contextPath);
                System.out.println(servletPath);
                System.out.println(servletContext);

                String fileName = request.getSession().getServletContext().getRealPath("/") + "upload/"
                        + file.getOriginalFilename();

                // 2选1
                // file.transferTo(new File(fileName));
                FileUtils.writeByteArrayToFile(new File(fileName), file.getBytes());

            }catch (Exception e){
                log.info("upload file error");
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public ResponseEntity download(HttpServletRequest request){
//        request.getSession().getServletContext("/") + "upload/";
//    }

}
