package dev.ericksuarez.esblog.post.service.repository;

import dev.ericksuarez.esblog.post.service.model.Category;
import dev.ericksuarez.esblog.post.service.model.Post;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Nested
    @DisplayName("Test save category")
    class SavePost {

        @Test
        public void save_postOk_success() {
            val category = Category.builder()
                    .id(1L)
                    .build();
            val post = Post.builder()
                    .category(category)
                    .user("user-id")
                    .title("Hello world")
                    .content("Content on java")
                    .url("hello-world")
                    .build();

            val postSaved = postRepository.save(post);

            assertThat(postSaved.getId(), notNullValue());
            assertThat(postSaved.getCategory().getId(), is(1L));
            assertThat(postSaved, allOf(
                    hasProperty("user", is("user-id")),
                    hasProperty("title", is("Hello world")),
                    hasProperty("content", is("Content on java")),
                    hasProperty("url", is("hello-world"))
            ));
        }

        @Test
        public void save_postEmpty_ThrowException() {
            val post = Post.builder().build();

            val exception = assertThrows(ConstraintViolationException.class,
                    () -> postRepository.save(post));

            assertThat(exception.getConstraintViolations(), hasSize(2));
            assertTrue(exception.getMessage().contains("Title is required"));
            assertTrue(exception.getMessage().contains("Content is required"));
        }

        @Test
        public void save_postUrlDuplicated_throw() {
            val category = Category.builder().id(1L).build();

            val post = Post.builder()
                    .category(category)
                    .user("user-id")
                    .title("Hello world")
                    .content("Content on java")
                    .url("titulo-con-espacios")
                    .build();

            assertThrows(DataIntegrityViolationException.class,
                    () -> postRepository.save(post));
        }
    }

    @Nested
    @DisplayName("Test read Post")
    class ReadPost {

        @Test
        public void findById_postExist_returnPost() {
            val maybePost = postRepository.findById(1L);

            assertTrue(maybePost.isPresent());

            val post = maybePost.get();

            assertThat(post, allOf(
                    hasProperty("user", is("user-random")),
                    hasProperty("title", is("Titulo con espacios")),
                    hasProperty("content", is("Contenido")),
                    hasProperty("url", is("titulo-con-espacios"))
            ));
        }

        @Test
        public void findById_postNotExist_returnEmpty() {
            val maybePost = postRepository.findById(404L);

            assertFalse(maybePost.isPresent());
        }

        @Test
        public void findByUser_userExist_returnPostList() {
            val posts = postRepository.findByUser("user-random");

            assertThat(posts, hasSize(2));
        }

        @Test
        public void findByCategoryId_findByCategoryId_returnPostList() {
            val posts = postRepository.findByCategoryId(2L);

            assertThat(posts, hasSize(2));
        }
    }
}
