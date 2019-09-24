package dev.esuarez.controller;

import dev.esuarez.facade.FacadeUser;
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

import static dev.esuarez.config.KakeiboUri.API;
import static dev.esuarez.config.KakeiboUri.USERS_API;

@RestController
@RequestMapping(API)
public class UserController {

    private static final String REGISTER_PATH = "/register";

    @Autowired
    private UserService userService;

    @Autowired
    private FacadeUser facadeUser;

    @GetMapping(USERS_API)
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(USERS_API + "/{id}")
    User getUser(@PathVariable @Min(1) Long id){
        return userService.findUserById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(USERS_API)
    User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping(USERS_API + "/{id}")
    User updateUser(@Valid @RequestBody User user,@PathVariable @Min(1) Long id){
        return userService.saveOrUpdateUser(user, id);
    }

    @PatchMapping(USERS_API + "/{id}")
    User patch(@RequestBody Map<String, String> user, @PathVariable @Min(1) Long id){
        return userService.patchUser(user, id);
    }

    @DeleteMapping(USERS_API + "/{id}")
    ResponseEntity<?> deleteUser(@PathVariable @Min(1) Long id){
        return userService.deleteUser(id);
    }

    @PostMapping(REGISTER_PATH)
    User registerUser(@Valid @RequestBody User user){
        return facadeUser.registerUser(user);
    }

}
