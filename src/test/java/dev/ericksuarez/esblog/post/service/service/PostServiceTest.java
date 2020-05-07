package dev.ericksuarez.esblog.post.service.service;

import dev.ericksuarez.esblog.post.service.error.PostNotFoundException;
import dev.ericksuarez.esblog.post.service.model.Post;
import dev.ericksuarez.esblog.post.service.repository.PostRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    protected Post buildPost() {
        return Post.builder()
                .id(3L)
                .user("user-random")
                .title("Primer Post")
                .content("Este es el primer post que veras aqui Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris at imperdiet tellus. Fusce maximus cursus mi. Suspendisse mollis erat a dolor iaculis, ac semper arcu luctus. Donec neque lorem, aliquam a enim et, mollis auctor nibh. Vestibulum porttitor pellentesque libero, sit amet interdum sem iaculis ac. In sapien nisi, eleifend.")
                .active(true)
                .url("primer-post")
                .build();
    }

    protected List<Post> buildListPost() {
        return Arrays.asList(
                buildPost(),
                Post.builder().content("Contenido").build(),
                Post.builder().content("Segundo contenido").build());
    }

    @Nested
    @DisplayName("Test to read Posts")
    class ReadPost {

        @Test
        public void getPostById_postExist_returnPost() {
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.of(buildPost()));

            val post = postService.getPostById(1L);

            assertThat(post.getId()).isNotNull();
            assertThat(post.getTitle()).isEqualTo("Primer Post");
            assertThat(post.getContent()).isNotNull();
            assertTrue(post.isActive());
        }

        @Test
        public void getPostById_postNotExist_throwException() {
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            val exception = assertThrows(PostNotFoundException.class,
                    () -> postService.getPostById(99L));

            assertThat(exception.getMessage().contains("Category not exist"));
        }

        @Test
        public void getPostByUrl_postNotExist_throwException() {
            when(postRepository.findByUrlAndActive(anyString(), anyBoolean()))
                    .thenReturn(Optional.of(buildPost()));

            val post = postService.getPostByUrl("url");

            assertThat(post.getId()).isNotNull();
            assertThat(post.getTitle()).isEqualTo("Primer Post");
            assertThat(post.getContent()).isNotNull();
            assertTrue(post.isActive());
        }

        @Test
        public void getPostPage_postsExist_returnPosts() {
            Page<Post> posts = new PageImpl<>(buildListPost());

            when(postRepository.findAllByActive(anyBoolean(), any(PageRequest.class)))
                    .thenReturn(posts);

            val pagePost = postService.getPostPage(PageRequest.of(0, 3));

            assertThat(pagePost.getTotalElements()).isEqualTo(3L);
            pagePost.get()
                    .forEach(post -> assertThat(post.getContent().length()).isLessThanOrEqualTo(50));
        }

        @Test
        public void getPostByCategory_categoryExist_returnPost() {
            Page<Post> posts = new PageImpl<>(buildListPost());

            when(postRepository.findByCategoryIdAndActive(anyLong(), anyBoolean(), any(PageRequest.class)))
                    .thenReturn(posts);

            val pagePost = postService.getPostByCategory(2L, PageRequest.of(0, 3));

            assertThat(pagePost.getTotalElements()).isEqualTo(3L);
            pagePost.get()
                    .map(post -> assertThat(post.getContent().length()).isLessThan(20) );
        }

        @Test
        public void getPostsByUser_categoryExist_returnPost() {
            Page<Post> posts = new PageImpl<>(buildListPost());

            when(postRepository.findByUserAndActive(anyString(), anyBoolean(), any(PageRequest.class)))
                    .thenReturn(posts);

            val pagePost = postService.getPostsByUser("userRandom", PageRequest.of(0, 3));

            assertThat(pagePost.getTotalElements()).isEqualTo(3L);
            pagePost.get()
                    .map(post -> assertThat(post.getContent().length()).isLessThan(20) );
        }
    }

    @Nested
    @DisplayName("Test to save post")
    class SavePost {

        @Test
        public void savePost_postOk_returnPost() {
            val post = buildPost();
            when(postRepository.save(any(Post.class)))
                    .thenReturn(post);

            val postSaved = postService.savePost(post);

            assertThat(postSaved.getId()).isNotNull();
            assertThat(postSaved)
                    .hasFieldOrPropertyWithValue("user", "user-random")
                    .hasFieldOrPropertyWithValue("title", "Primer Post")
                    .hasFieldOrPropertyWithValue("active", true)
                    .hasFieldOrPropertyWithValue("url", "primer-post");
        }

        @Test
        public void savePost_postEmpty_returnPost() {
            val post = buildPost();
            when(postRepository.save(any(Post.class)))
                    .thenThrow(ConstraintViolationException.class);

            val exception = assertThrows(ConstraintViolationException.class,
                    () -> postService.savePost(post));

            System.out.println(exception);
        }
    }

    @Nested
    @DisplayName("Test to delete post")
    class DeletePost {

        @Test
        public void delete_postExist_success() {
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.of(buildPost()));

            ResponseEntity response = postService.deletePost(1L);

            assertThat(response).isEqualTo(ResponseEntity.ok().build());
        }

        @Test
        public void delete_postNotExist_success() {
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            val exception = assertThrows(PostNotFoundException.class,
                    () -> postService.deletePost(99L));

            assertThat(exception.getMessage().contains("Category not exist"));
        }
    }
}
