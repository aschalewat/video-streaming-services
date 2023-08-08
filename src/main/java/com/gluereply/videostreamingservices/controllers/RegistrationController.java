package com.gluereply.videostreamingservices.controllers;

import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.RegestrationService;
import com.gluereply.videostreamingservices.services.impl.RegistrationServiceImpl;
import com.gluereply.videostreamingservices.services.validation.RejectionReason;
import com.gluereply.videostreamingservices.services.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/users")
public class RegistrationController {

    @Autowired
    RegestrationService registrationService;


    @PostMapping("/register")
    ResponseEntity<ValidationResult> register(@RequestBody User user){
       ValidationResult result = registrationService.register(user);
       if (result.getRejectionReasons().size() == 0){
           return ResponseEntity.status(HttpStatus.CREATED).body(result);
       } else if(result.getRejectionReasons().contains(RejectionReason.UNDER_AGE)){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        } else if(result.getRejectionReasons().contains(RejectionReason.USERNAME_EXISTS)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
       }
    }

    @GetMapping("/get")
    ResponseEntity<List<User>> getUsers(@RequestParam("filter") String filter){
        Boolean creditCardFilter = null;
        if ("yes".equalsIgnoreCase(filter)){
            creditCardFilter = true;
        } else if ("no".equalsIgnoreCase(filter)){
            creditCardFilter = false;
        }
        List<User> users = registrationService.getUsers(creditCardFilter);
        return ResponseEntity.ok(users);
    }
}
