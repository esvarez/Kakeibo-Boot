package dev.ericksuarez.esblog.post.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.esblog.post.service.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper =  new ObjectMapper();

    @Nested
    @DisplayName("Test to create Category")
    class SaveCategory {

        @Test
        void saveCategory_categoryOk_returnNewCategory() throws Exception {
            var category = Category.builder().name("Framework").build();

            mockMvc.perform(post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(category)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is("Framework")))
                    .andDo(print());
        }

        @Test
        void saveCategory_categoryEmpty_throwError() throws Exception {
            var category = Category.builder().build();

            mockMvc.perform(post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(category)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(400)))
                    .andExpect(jsonPath("$.errors", hasSize(1)))
                    .andExpect(jsonPath("$.errors", hasItem("Name is required")))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Test to read Categories")
    class ReadCategory {

        @Test
        public void getCategoryById_idExist_returnCategory() throws Exception {
            var id = 1;

            mockMvc.perform(get("/categories/" + id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id)))
                    .andExpect(jsonPath("$.name", is("Java")))
                    .andDo(print());
        }

        @Test
        public void getCategoryById_idNotExist_returnError() throws Exception {
            var id = 404;

            mockMvc.perform(get("/categories/" + id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status", is(404)))
                    .andExpect(jsonPath("$.error", is("Category id 404 not found.")))
                    .andDo(print());
        }

        @Test
        void getAllCategories_categoriesExist_returnCategoriesList() throws Exception {
            mockMvc.perform(get("/categories")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andDo(print());
        }
    }


    @Nested
    @DisplayName("Test to update Category")
    class UpdateCategory {

        @Test
        void updateCategory_categoryOk_returnCategory() throws Exception {
            var id = 2;
            var category = Category.builder().name("Education").build();

            mockMvc.perform(put("/categories/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(category)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id)))
                    .andExpect(jsonPath("$.name", is("Education")))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("Delete Category")
    class DeleteCategory {

        @Test
        void deleteCategory_categoryExist_returnCategory() throws Exception {
            var id = 3;

            mockMvc.perform(delete("/categories/" + id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        void deleteCategory_categoryNotExist_returnCategory() throws Exception {
            var id = 40;

            mockMvc.perform(delete("/categories/" + id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}
