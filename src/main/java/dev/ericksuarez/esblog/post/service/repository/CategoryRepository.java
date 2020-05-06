package dev.ericksuarez.esblog.post.service.repository;

import dev.ericksuarez.esblog.post.service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByNameAsc();
}
