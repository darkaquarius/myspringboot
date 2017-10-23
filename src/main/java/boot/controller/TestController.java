package boot.controller;

import boot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huishen on 17/5/8.
 */
@RequestMapping("/mytest")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/handle")
    public ResponseEntity<String> handle(HttpEntity<String> requestEntity) {
        String myRequestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader");
        String body = requestEntity.getBody();
        System.out.println(body);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "myValue");
        return new ResponseEntity<String>("HelloWorld", responseHeaders, HttpStatus.OK);
    }

    @RequestMapping("/test1")
    public void test1() {
        testService.test1();
    }

    @RequestMapping(value = "test2")
    public void test2(HttpServletRequest request) {
        System.out.println("test2");
    }

    @RequestMapping(value = "test3")
    public void test3(HttpServletRequest request) {
        System.out.println("test3");
    }

}
