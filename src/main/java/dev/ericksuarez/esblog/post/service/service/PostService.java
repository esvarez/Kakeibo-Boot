package dev.ericksuarez.esblog.post.service.service;

import dev.ericksuarez.esblog.post.service.model.Post;
import dev.ericksuarez.esblog.post.service.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getPostPage(Pageable pageable) {
        postRepository.findAll(pageable);
        return null;
    }

    public Post getPostById(Long postId) {
        postRepository.findById(postId);
        return null;
    }

    public Page<Post> getPostsByUser(String userId) {
        //postRepository.findByUser(userId);
        return null;
    }

    public Page<Post> getPostByCategory(Long categoryId) {
        //postRepository.findByCategoryId(categoryId);
        return null;
    }

    public Post savePost(Post post) {
        return null;
    }

    public ResponseEntity<?> deletePost(Long postId) {
        return null;
    }
}
