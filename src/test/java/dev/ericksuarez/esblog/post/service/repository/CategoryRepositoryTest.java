package dev.ericksuarez.esblog.post.service.repository;

import dev.ericksuarez.esblog.post.service.model.Category;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Nested
    @DisplayName("Test save Category")
    class SaveCategory {

        @Test
        public void save_categoryOk_success() {
            val category = Category.builder()
                    .name("Angular")
                    .build();
            val categorySaved = categoryRepository.save(category);

            assertThat(categorySaved.getId(), notNullValue());
            assertThat(categorySaved.getName(), is("Angular"));
            assertThat(categorySaved.getCreatedAt(), notNullValue());
            assertThat(categorySaved.getUpdatedAt(), notNullValue());
        }

        @Test
        public void save_categoryEmpty_throwException() {
            val category = Category.builder().build();

            assertThrows(DataIntegrityViolationException.class,
                    ()-> categoryRepository.save(category));
        }

        @Test
        public void save_categoryExist_updateValue() {
            val category = categoryRepository.findById(2L).get();

            val name = category.getName();
            val date = category.getCreatedAt();

            val categoryToSave = Category.builder()
                    .id(category.getId())
                    .name("New value")
                    .build();

            val categoryUpdated = categoryRepository.save(categoryToSave);

            assertThat(categoryUpdated.getId(), is(category.getId()));
            assertThat(categoryUpdated.getName(), not(name));
            assertThat(categoryUpdated.getName(), is("New value"));
            assertThat(categoryUpdated.getUpdatedAt(), not(date));
        }
    }

    @Nested
    @DisplayName("Test read Category")
    class ReadCategory {

        @Test
        public void findById_categoryExist_returnCategory() {
            val maybeCategory = categoryRepository.findById(2L);

            assertThat(maybeCategory.isPresent(), is(true));

            val category = maybeCategory.get();

            assertThat(category.getId(), is(2L));
            assertThat(category.getName(), is("Tech"));
        }

        @Test
        public void findById_categoryNotExist_returnEmpty() {
            val maybeCategory = categoryRepository.findById(404L);
            assertThat(maybeCategory.isPresent(), is(false));
        }
    }

    @Nested
    @DisplayName("Test Delete category")
    class DeleteCategory {

        @Test
        public void delete_categoryExist_throwException() {
            val maybeCategory = categoryRepository.findById(1L);
            assertThat(maybeCategory.isPresent(), is(true));
            val category = maybeCategory.get();

            assertThrows(DataIntegrityViolationException.class,
                    () -> categoryRepository.delete(category));
        }

        @Test
        public void delete_categoryEmpty_success() {
            val category = Category.builder().build();

            categoryRepository.delete(category);
        }
    }
}
