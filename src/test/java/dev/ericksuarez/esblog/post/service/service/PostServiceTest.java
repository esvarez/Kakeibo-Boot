package dev.ericksuarez.esblog.post.service.service;

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

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
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
                .title("Primer Post")
                .content("Este es el primer post que veras aqui Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris at imperdiet tellus. Fusce maximus cursus mi. Suspendisse mollis erat a dolor iaculis, ac semper arcu luctus. Donec neque lorem, aliquam a enim et, mollis auctor nibh. Vestibulum porttitor pellentesque libero, sit amet interdum sem iaculis ac. In sapien nisi, eleifend.")
                .active(true)
                .url("primer-post")
                .build();
    }

    protected List<Post> buildListPost() {
        return Arrays.asList(
                buildPost(),
                Post.builder().build(),
                Post.builder().build());
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

            val exception = assertThrows(RuntimeException.class,
                    () -> postService.getPostById(99L));

            assertTrue(exception.getMessage().contains("Category not exist"));
        }

        @Test
        public void getPostPage_postsExist_returnPosts() {
            Page<Post> posts = new PageImpl<>(buildListPost());

            when(postRepository.findAll(any(PageRequest.class)))
                    .thenReturn(posts);
        }

        @Test
        public void getPostByCategory_categoryExist_returnPost() {

        }

        @Test
        public void getPostsByUser_categoryExist_returnPost() {

        }
    }
}
