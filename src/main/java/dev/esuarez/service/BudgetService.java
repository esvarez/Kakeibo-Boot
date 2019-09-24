package dev.esuarez.service;

import dev.esuarez.error.budget.BudgetNotFoundException;
import dev.esuarez.model.Budget;
import dev.esuarez.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> getAllBudgets(){
        return budgetRepository.findAll();
    }

    public Budget findBudgetById(Long id){
        return budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException(id));
    }

    public Budget createBudget(Budget budget){
        return budgetRepository.save(budget);
    }

    public Budget saveOrUpdateBudget(Long id, Budget budgetRequest){
        return budgetRepository.findById(id)
                .map(budget -> {
                    budget.setUser(budgetRequest.getUser());
                    budget.setCategory(budgetRequest.getCategory());
                    budget.setName(budgetRequest.getName());
                    budget.setMonth(budgetRequest.getMonth());
                    budget.setAmount(budgetRequest.getAmount());
                    budget.setMaxAmount(budgetRequest.getMaxAmount());
                    return budgetRepository.save(budget);
                })
                .orElseGet(() -> {
                    budgetRequest.setId(id);
                    return budgetRepository.save(budgetRequest);
                });
    }

    // ToDo patch method

    public ResponseEntity<?> deleteBudget(Long id){
        return budgetRepository.findById(id)
                .map(budget -> {
                    budgetRepository.delete(budget);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new BudgetNotFoundException(id));
    }

}
