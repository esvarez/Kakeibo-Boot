package dev.esuarez.error.Roll;

public class RollNotFoundException extends RuntimeException {
    public RollNotFoundException(Integer id) {
        super("Roll id not found: " + id);
    }
}
