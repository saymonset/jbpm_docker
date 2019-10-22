package com.rest;

import com.dao.User;
import com.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by simon on 21/10/19.
 */
@RestController
@RequestMapping("/simon")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class Test {
    @Inject
    private UserRepository userRepository;
   


    public Test(){}

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<?> procesar() {
       List<User> users = (List<User>)userRepository.findAll();
      for (User u:users){
          System.out.println("u.getLoginName() = " + u.getFirstName());
      }
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }
    
    
}
