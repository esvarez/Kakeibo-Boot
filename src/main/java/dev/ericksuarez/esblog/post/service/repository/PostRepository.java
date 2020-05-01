package dev.ericksuarez.esblog.post.service.repository;

import dev.ericksuarez.esblog.post.service.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);

    List<Post> findByUser(String userId);
}
