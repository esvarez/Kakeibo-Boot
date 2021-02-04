package dev.ericksuarez.paper.posting.service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ericksuarez.paper.posting.service.model.Post;
import dev.ericksuarez.paper.posting.service.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PostRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void whenFindByUrl_thenReturnPost() {
        var user = User.builder()
                .user("New user")
                .password("pass")
                .build();
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        var firstPost = Post.builder()
                .title("First Post")
                .url("first-post")
                .user(user)
                .build();
        firstPost.setCreatedAt(new Date());
        firstPost.setUpdatedAt(new Date());

        entityManager.persist(user);
        entityManager.persist(firstPost);
        entityManager.flush();
        Optional<Post> found = postRepository.findByUrl(firstPost.getUrl());

        assertThat(found.isPresent())
                .isTrue();
        assertThat(found.get().getTitle())
                .isEqualTo(firstPost.getTitle());
    }
}
