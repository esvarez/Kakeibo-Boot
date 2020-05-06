package dev.ericksuarez.esblog.post.service.service;

import dev.ericksuarez.esblog.post.service.error.CategoryNotFoundException;
import dev.ericksuarez.esblog.post.service.model.Category;
import dev.ericksuarez.esblog.post.service.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        log.info("event=findAllCategoriesInvoked");
        List<Category> categories = categoryRepository.findAll();
        log.info("event=findAllCategoriesInvoked categories={}", categories);
        return categories;
    }

    public Category getCategoryById(Long categoryId) {
        log.info("event=getCategoryByIdInvoked categoryId={}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("event=categoryNotExist categoryId={}", categoryId);
                    return new CategoryNotFoundException(categoryId);
                });
        log.info("event=categoryGot category={}", category);
        return category;
    }

    public Category saveCategory(Category category) {
        log.info("event=saveCategoryInvoked category={}", category);
        Category categorySaved = categoryRepository.save(category);
        log.info("event=categorySaved categorySaved={}", categorySaved);
        return category;
    }

    public Category updateCategory(Long categoryId, Category category) {
        log.info("event=updateCategoryInvoked categoryId={} category={}", categoryId ,category);
        category.setId(categoryId);
        return saveCategory(category);
    }

    public ResponseEntity<?> deleteCategory(Long categoryId) {
        var category = getCategoryById(categoryId);
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }
}
