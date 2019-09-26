package dev.esuarez.facade;

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

        Optional<Roll> optionalRoll = rollRepository.findByName("User");

        if (optionalRoll.isPresent()){
            Roll roll = optionalRoll.get();

            Set<Roll> rolls = new HashSet<>();
            rolls.add(roll);

            user.setRolls(rolls);

            //user.getRolls().add(roll);

            System.out.println(user);
            roll.getUsers().add(user);

            user.setActive(true);

            System.out.println(roll);
            System.out.println(user);

            return userRepository.save(user);
        }else {
            return null;
        }
    }
}
