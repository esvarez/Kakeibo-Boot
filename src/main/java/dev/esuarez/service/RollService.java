package dev.esuarez.service;

import dev.esuarez.error.Roll.RollNotFoundException;
import dev.esuarez.model.Roll;
import dev.esuarez.repository.RollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;

    public List<Roll> getAllRolls(){
        return rollRepository.findAll();
    }

    public Roll findRolById(Integer id){
        return rollRepository.findById(id)
                .orElseThrow(() -> new RollNotFoundException(id));
    }

    public Roll createRoll(Roll roll){
        return rollRepository.save(roll);
    }

    public Roll saveOrUpdateRoll(Integer id, Roll rollRequest){
        return rollRepository.findById(id)
                .map(roll -> {
                    /*
                    roll.setName(rollRequest.getName());
                    roll.setDescription(rollRequest.getDescription());
                    */
                    return rollRepository.save(roll);
                })
                .orElseThrow(() -> new RollNotFoundException(id));
    }

    // ToDo Patch

    public ResponseEntity<?> deleteRoll(Integer id){
        return rollRepository.findById(id)
                .map(roll -> {
                    rollRepository.delete(roll);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new RollNotFoundException(id));
    }
}
