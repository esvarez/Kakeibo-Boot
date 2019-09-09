package dev.esuarez.service;

import dev.esuarez.error.movement.MovementNotFound;
import dev.esuarez.model.Movement;
import dev.esuarez.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    public List<Movement> getAllMovements(){
        return movementRepository.findAll();
    }

    public Movement findMovementById(Long id){
        return movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFound(id));
    }

    public Movement createMovement (Movement movement){
        return movementRepository.save(movement);
    }

    public Movement saveOrUpdateMovement(Movement movementRequest, Long id){
        return movementRepository.findById(id)
                .map(movement -> {
                    movement.setFrom(movementRequest.getFrom());
                    movement.setTo(movementRequest.getTo());
                    movement.setAmount(movementRequest.getAmount());
                    movement.setCategory(movementRequest.getCategory());
                    movement.setDate(movement.getDate());
                    return movementRepository.save(movement);
                }).orElseGet(() -> {
                    movementRequest.setId(id);
                    return movementRepository.save(movementRequest);
                });
    }

    public ResponseEntity<?> deleteMovement(Long id){
        return movementRepository.findById(id)
                .map(movement -> {
                    movementRepository.delete(movement);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new MovementNotFound(id));
    }
}
