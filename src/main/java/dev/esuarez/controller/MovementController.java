package dev.esuarez.controller;

import dev.esuarez.model.Movement;
import dev.esuarez.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static dev.esuarez.config.KakeiboUri.API;
import static dev.esuarez.config.KakeiboUri.MOVEMENT_API;

@RestController
@RequestMapping(API)
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping(MOVEMENT_API)
    List<Movement> getAllMovements(){
        return movementService.getAllMovements();
    }

    @GetMapping(MOVEMENT_API + "/{id}")
    Movement getMovementById(@PathVariable @Min(1) Long id){
        return movementService.findMovementById(id);
    }

    @PostMapping(MOVEMENT_API)
    Movement createMovement(@Valid @RequestBody Movement movement){
        return movementService.createMovement(movement);
    }

    @PutMapping(MOVEMENT_API + "/{id}")
    Movement saveOrUpdateMovement(@Valid @RequestBody Movement movement, @PathVariable @Min(1) Long id){
        return movementService.saveOrUpdateMovement(movement, id);
    }

    @DeleteMapping(MOVEMENT_API + "/{id}")
    ResponseEntity<?> deleteMovement(@PathVariable @Min(1) Long id){
        return movementService.deleteMovement(id);
    }

}
