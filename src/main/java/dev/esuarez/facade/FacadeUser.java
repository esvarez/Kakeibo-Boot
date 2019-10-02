package dev.esuarez.facade;

import dev.esuarez.error.Roll.RollNotFoundException;
import dev.esuarez.model.Roll;
import dev.esuarez.model.User;
import dev.esuarez.repository.RollRepository;
import dev.esuarez.repository.UserRepository;
import dev.esuarez.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacadeUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RollRepository rollRepository;

    public User registerUser(User user){
        String rollName = "User";
        Encryptor encryptor = Encryptor.getEncryptorInstance();

        Roll roll = rollRepository.findByName(rollName)
                .orElseThrow( () -> new RollNotFoundException(rollName));

        user.setActive(true);
        user.getRolls().add(roll);
        user.setPassword(encryptor.encrypt(user.getPassword()));

        return userRepository.save(user);
    }
}
