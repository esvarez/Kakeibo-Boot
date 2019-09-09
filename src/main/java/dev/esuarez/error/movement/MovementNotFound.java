package dev.esuarez.error.movement;

public class MovementNotFound extends RuntimeException {
    public MovementNotFound(Long id) {
        super("Movement id not found: " + id);
    }
}
