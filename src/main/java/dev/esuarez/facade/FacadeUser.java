package dev.esuarez.facade;

import dev.esuarez.error.Roll.RollNotFoundException;
import dev.esuarez.model.Roll;
import dev.esuarez.model.User;
import dev.esuarez.repository.RollRepository;
import dev.esuarez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FacadeUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RollRepository rollRepository;

    public User registerUser(User user){
        String rollName = "User";

        user.setActive(true);
        Optional<Roll> optionalRoll = rollRepository.findByName(rollName);

        optionalRoll.ifPresent(roll -> {
            user.getRolls().add(roll);
        });

        if (optionalRoll.isPresent()){
            return userRepository.save(user);
        }
        return null;

    }
}
