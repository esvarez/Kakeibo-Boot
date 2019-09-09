package dev.esuarez.error.movementcategory;

public class MovementCategoryNotFoundException extends RuntimeException {
    public MovementCategoryNotFoundException(Long id) {
        super("Category id not found: " + id);
    }
}
