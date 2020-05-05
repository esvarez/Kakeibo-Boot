package dev.ericksuarez.esblog.post.service.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity) {
        super(entity + " not found.");
    }
}
