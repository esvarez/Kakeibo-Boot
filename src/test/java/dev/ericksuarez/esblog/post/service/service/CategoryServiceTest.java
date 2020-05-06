package dev.ericksuarez.esblog.post.service.service;

import dev.ericksuarez.esblog.post.service.error.CategoryNotFoundException;
import dev.ericksuarez.esblog.post.service.model.Category;
import dev.ericksuarez.esblog.post.service.repository.CategoryRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    protected Category buildCategory() {
        return Category.builder()
                .id(7L)
                .name("Education")
                .build();
    }

    @Nested
    @DisplayName("Test to save and update categories")
    class SaveCategory {

        @Test
        public void saveCategory_categoryOk_returnCategory() {
            val category = Category.builder()
                    .name("Tech")
                    .build();

            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(Category.builder()
                            .id(99L)
                            .name("Tech")
                            .build());

            val categorySaved = categoryService.saveCategory(category);

            assertThat(categorySaved).hasFieldOrProperty("id");
            assertThat(categorySaved.getName()).isEqualTo("Tech");
        }

        @Test
        void saveCategory_categoryExist_returnCategory() {
            val categoryToUpdate = buildCategory();

            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(buildCategory());

            val category = categoryService.saveCategory(categoryToUpdate);

            assertThat(category).hasFieldOrProperty("id");
            assertThat(category.getName()).isEqualTo("Education");
        }
    }

    @Nested
    @DisplayName("Test to read Categories")
    class FindCategory {

        @Test
        public void findAllCategories_returnListCategories() {
            when(categoryRepository.findAll())
                    .thenReturn(Arrays.asList(
                            Category.builder().build(),
                            Category.builder().build(),
                            Category.builder().build()));

            val categories = categoryService.findAllCategories();

            assertThat(categories).hasSize(3);
        }

        @Test
        void getCategoryById_categoryExist_returnCategory() {
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(buildCategory()));

            val category = categoryService.getCategoryById(7L);

            assertThat(category).hasFieldOrProperty("id");
            assertThat(category.getName()).isEqualTo("Education");
        }

        @Test
        void getCategoryById_categoryNotExist_returnThrow() {
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            val exception = assertThrows(CategoryNotFoundException.class,
                    () -> categoryService.getCategoryById(99L));

            assertTrue(exception.getMessage().matches("Category id .*. not found.*"));
        }
    }

    @Nested
    @DisplayName("Test to delete Categories")
    class DeleteCategory {

        @Test
        public void delete_categoryExist_success() {
            when(categoryRepository.findById(anyLong()))
                        .thenReturn(Optional.of(buildCategory()));

            ResponseEntity response = categoryService.deleteCategory(1L);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        public void delete_categoryNotExist_throw() {
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            val exception = assertThrows(CategoryNotFoundException.class,
                    () -> categoryService.deleteCategory(99L));

            assertTrue(exception.getMessage().matches("Category id .*. not found.*"));
        }
    }
}
