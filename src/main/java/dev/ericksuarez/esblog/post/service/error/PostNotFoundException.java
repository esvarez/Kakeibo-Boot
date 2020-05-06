package dev.ericksuarez.esblog.post.service.error;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(Long id) {
        super("Post id " + id );
    }

    public PostNotFoundException(String url) {
        super("Post url " + url );
    }
}
