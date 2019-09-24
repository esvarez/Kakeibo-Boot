package dev.esuarez.repository;

import dev.esuarez.model.MovementCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementCategoryRepository extends JpaRepository<MovementCategory, Long> {
}
