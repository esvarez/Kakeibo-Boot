package dev.esuarez.facade;

import dev.esuarez.model.Roll;
import dev.esuarez.model.User;
import dev.esuarez.repository.RollRepository;
import dev.esuarez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

            user.getRolls().add(roll);
            roll.getUsers().add(user);

            System.out.println(roll);


            userRepository.save(user);

            //rollRepository.save(roll);



            return null;
        }else {
            return null;
        }
    }
}
