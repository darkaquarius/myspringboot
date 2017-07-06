package boot.controller;

import boot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huishen on 17/7/6.
 * bookService通过rmi远程调用
 */

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "sayBook")
    public String sayBook() {
        return bookService.sayBook();
    }

}
