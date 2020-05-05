package dev.ericksuarez.esblog.post.service.repository;

import dev.ericksuarez.esblog.post.service.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Post> findByUser(String userId, Pageable pageable);
}
