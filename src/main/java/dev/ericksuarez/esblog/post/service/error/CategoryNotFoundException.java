package dev.ericksuarez.esblog.post.service.error;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(Long id) {
        super("Category id " + id );
    }
}
