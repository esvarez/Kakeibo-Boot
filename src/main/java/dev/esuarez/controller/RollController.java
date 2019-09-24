package dev.esuarez.controller;

import dev.esuarez.model.Roll;
import dev.esuarez.service.RollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static dev.esuarez.config.KakeiboUri.*;

@RestController
@RequestMapping(API)
public class RollController {

    @Autowired
    private RollService rollService;

    @GetMapping(ROLL_API)
    List<Roll> getAllRoles(){
        return rollService.getAllRolls();
    }

    @GetMapping(ROLL_API + "/{id}")
    Roll findRollById(@PathVariable @Min(1) Integer id){
        return rollService.findRolById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(ROLL_API)
    Roll createRoll(@Valid @RequestBody Roll roll){
        return rollService.createRoll(roll);
    }

    @PutMapping(ROLL_API + "/{id}")
    Roll saveOrupdateRoll(@PathVariable @Min(1) Integer id,
                          @Valid @RequestBody Roll roll){
        return rollService.saveOrUpdateRoll(id, roll);
    }

    //ToDo Patch

    @DeleteMapping(ROLL_API + "/{id}")
    ResponseEntity<?> deleteRoll(@PathVariable @Min(1) Integer id){
        return rollService.deleteRoll(id);
    }

}
