package dev.ericksuarez.paper.posting.service.repository;

import dev.ericksuarez.paper.posting.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
