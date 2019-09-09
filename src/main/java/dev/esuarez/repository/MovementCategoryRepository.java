package dev.esuarez.repository;

import dev.esuarez.model.MovementCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementCategoryRepository extends JpaRepository<MovementCategory, Long> {
}
