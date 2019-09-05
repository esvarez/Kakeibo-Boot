package dev.esuarez.service;

import dev.esuarez.error.user.UserNotFoundException;
import dev.esuarez.error.user.UserUnsupportedFieldPatchException;
import dev.esuarez.model.User;
import dev.esuarez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User saveOrUpdateUser(User userRequest, Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUser(userRequest.getUser());
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    userRequest.setId(id);
                    return userRepository.save(userRequest);
                });
    }

    public User patchUser(Map<String, String> update, Long id){
        return userRepository.findById(id)
                .map(x -> {
                    String user = update.get("user");
                    if (!StringUtils.isEmpty(user)){
                        x.setUser(user);
                        return userRepository.save(x);
                    } else {
                        throw new UserUnsupportedFieldPatchException(update.keySet());
                    }
                })
                .orElseGet(() -> {
                    throw new UserNotFoundException(id);
                });
    }

    public ResponseEntity<?> deleteUser(Long id){
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

}
