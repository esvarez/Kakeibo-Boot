package dev.esuarez.service;

import dev.esuarez.error.account.AccountNotFoundException;
import dev.esuarez.error.movement.MovementNotFound;
import dev.esuarez.error.movementcategory.MovementCategoryNotFoundException;
import dev.esuarez.model.Account;
import dev.esuarez.model.Movement;
import dev.esuarez.repository.AccountRepository;
import dev.esuarez.repository.MovementCategoryRepository;
import dev.esuarez.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MovementCategoryRepository movementCategoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Movement> getAllMovements(){
        return movementRepository.findAll();
    }

    public Movement findMovementById(Long id){
        return movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFound(id));
    }

    public Movement createMovement (Movement movement){
        Long categoryId = movement.getCategory().getId();

        Account account = accountRepository.findById(movement.getFromAccount().getId())
                .orElseThrow(() -> new AccountNotFoundException(movement.getFromAccount().getId()));

        Account toaccount = accountRepository.findById(movement.getToAccount().getId())
                .orElseThrow(() -> new AccountNotFoundException(movement.getToAccount().getId()));

        return movementCategoryRepository.findById(categoryId)
                .map(category -> {
                    movement.setCategory(category);
                    movement.setFromAccount(account);
                    movement.setToAccount(toaccount );
                    return movementRepository.save(movement);
                }).orElseThrow(() -> new MovementCategoryNotFoundException(categoryId));
    }

    public Movement saveOrUpdateMovement(Movement movementRequest, Long id){
        return movementRepository.findById(id)
                .map(movement -> {
                    /*
                    movement.setFrom(movementRequest.getFrom());
                    movement.setTo(movementRequest.getTo());
                    movement.setAmount(movementRequest.getAmount());
                    */
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
