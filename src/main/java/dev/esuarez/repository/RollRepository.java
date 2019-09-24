package dev.esuarez.repository;

import dev.esuarez.model.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RollRepository extends JpaRepository<Roll, Integer> {

    Optional<Roll> findByName(String name);
}
