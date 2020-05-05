package dev.ericksuarez.esblog.post.service.repository;

import dev.ericksuarez.esblog.post.service.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Optional<Post> findByUrlAndActive(String url, boolean active);

    Page<Post> findAllByActive(boolean active, Pageable pageable);

    Page<Post> findByCategoryIdAndActive(Long categoryId, boolean active, Pageable pageable);

    Page<Post> findByUserAndActive(String userId, boolean active, Pageable pageable);
}
