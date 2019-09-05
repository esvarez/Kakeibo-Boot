package dev.esuarez.controller;

import dev.esuarez.model.User;
import dev.esuarez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable @Min(1) Long id){
        return userService.findUserById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    User updateUser(@Valid @RequestBody User user,@PathVariable @Min(1) Long id){
        return userService.saveOrUpdateUser(user, id);
    }

    @PatchMapping("/users/{id}")
    User patch(@RequestBody Map<String, String> user, @PathVariable @Min(1) Long id){
        return userService.patchUser(user, id);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable @Min(1) Long id){
        return userService.deleteUser(id);
    }

}
