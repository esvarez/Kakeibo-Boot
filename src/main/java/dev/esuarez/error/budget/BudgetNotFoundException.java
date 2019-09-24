package dev.esuarez.error.budget;

public class BudgetNotFoundException extends RuntimeException {
    public BudgetNotFoundException(Long id) {
        super("Budget id not found: " + id);
    }
}
