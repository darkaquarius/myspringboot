package boot.controller;

import boot.domain.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import boot.service.UserService;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by huishen on 16/9/24.
 */

@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user){
        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/get_user/{id}", method =  RequestMethod.GET)
    public User getUserById(@PathVariable("id") int id) throws InvocationTargetException, IllegalAccessException {
        return userService.getUserById(id);
    }
}
