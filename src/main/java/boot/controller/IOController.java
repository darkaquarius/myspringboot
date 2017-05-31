package boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by huishen on 16/11/14.
 */
@Controller
@RequestMapping("/download")
public class IOController {

    private String fileUrl = "/data/temp/";

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(){
        //注意,正式地址!!!!!!!!!!!!!
        String connectUrl = "http://dc.chuangqish.cn";
        int appId = 20305;
        LocalDate dateBegin = LocalDate.of(2016, 11, 1);
        LocalDate dateEnd = LocalDate.of(2016, 11, 14);
        boolean state = true;

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/datas/export_all")
            .queryParam("app_id", appId)
            .queryParam("start_time", dateBegin)
            .queryParam("end_time", dateEnd)
            .queryParam("state", state);

        String url = connectUrl + builder.toUriString();
        byte[] bytes = new RestTemplate().getForObject(url, byte[].class);

        String fileName = appId + "(" + dateBegin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ").xls";
        //pre java 7
        // BufferedOutputStream bo = null;
        // try{
        //     bo = new BufferedOutputStream(new FileOutputStream(fileUrl + fileName));
        //     bo.write(bytes);
        //     bo.flush();
        // }catch (FileNotFoundException e){
        //     e.printStackTrace();
        // }catch (IOException e){
        //     e.printStackTrace();
        // }finally{
        //     try{
        //         bo.close();
        //     }catch (IOException e){
        //         e.printStackTrace();
        //     }
        // }

        //java 7, try-with-resources
        try(BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(fileUrl + fileName))){
            bo.write(bytes);
            bo.flush();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam("filename") String fileName,
                         HttpServletRequest request, HttpServletResponse response){
        byte[] bytes = null;
        try {
            File file = new File(fileUrl + fileName);
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(file));
            bytes = new byte[(int) file.length()];
            bi.read(bytes);
            bi.close();
            file.delete();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        String userAgent = request.getHeader("User-Agent");
        String outName = new String(userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-disposition", "attachment; filename=\"" + outName + "\"");
        response.setCharacterEncoding("UTF-8");
        try (OutputStream os = response.getOutputStream()) {
            os.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/download2", method = RequestMethod.GET)
    public void download2(@RequestParam("filename") String fileName,
                          HttpServletRequest request, HttpServletResponse response){

    }


}
