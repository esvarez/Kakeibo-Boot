package dev.ericksuarez.esblog.post.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.esblog.post.service.model.Category;
import dev.ericksuarez.esblog.post.service.model.Post;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static dev.ericksuarez.esblog.post.service.config.UriConfig.GET_POST_BY_URL;
import static dev.ericksuarez.esblog.post.service.config.UriConfig.GET_POST_PAGE;
import static dev.ericksuarez.esblog.post.service.config.UriConfig.POSTS;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper =  new ObjectMapper();

    @Nested
    @DisplayName("Test to create Post")
    class CreatePost {

        @Test
        void savePost_postOk_returnNewPost() throws Exception {
            val post = Post.builder()
                    .user("nuevo-usuario")
                    .title("My new post")
                    .category(Category.builder().id(1L).build())
                    .content("El contenido de este post")
                    .build();

            mockMvc.perform(post(POSTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(notNullValue())))
                    .andExpect(jsonPath("$.title", is("My new post")))
                    .andExpect(jsonPath("$.url", is("my-new-post")))
                    .andExpect(jsonPath("$.active", is(true)))
                    .andDo(print());

        }

        @Test
        void savePost_postEmpty_returnError() throws Exception {
            val post = Post.builder().build();

            mockMvc.perform(post(POSTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(400)))
                    .andExpect(jsonPath("$.errors", hasSize(2)))
                    .andExpect(jsonPath("$.errors", hasItem("Title is required")))
                    .andExpect(jsonPath("$.errors", hasItem("Content is required")))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Test to read Post")
    class ReadPost {

        @Test
        public void getPostPage_paramsOk_returnPosts() throws Exception {

            mockMvc.perform(get(GET_POST_PAGE + "?page=0&size=3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(3)))
                    .andExpect(jsonPath("$.sort.sorted", is(true)))
                    .andDo(print());
        }

        @Test
        public void getPostPage_paramsOk_returnPost() throws Exception {

            mockMvc.perform(get(GET_POST_BY_URL + "/nuevo-titulo-mas2")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.user", is("other-id")))
                    .andExpect(jsonPath("$.title", is("nuevo titulo mas")))
                    .andExpect(jsonPath("$.content", is("Contenido")))
                    .andExpect(jsonPath("$.active", is(true)))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Test to update Post")
    class UpdatePost {

        @Test
        public void updatePost_postOk_returnPost() throws Exception {
            val id = 4;
            val post = Post.builder()
                    .user("nuevo-usuario")
                    .title("Post actualizado")
                    .category(Category.builder().id(1L).build())
                    .content("El contenido de este post")
                    .active(true)
                    .build();

            mockMvc.perform(put(POSTS + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(notNullValue())))
                    .andExpect(jsonPath("$.title", is("Post actualizado")))
                    .andExpect(jsonPath("$.url", is("post-actualizado")))
                    .andExpect(jsonPath("$.active", is(true)))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Test to delete Post")
    class DeletePost {

        @Test
        public void deletePost_idExist_success() throws Exception {
            val id = 5;

            mockMvc.perform(delete(POSTS + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
        }
    }
}
