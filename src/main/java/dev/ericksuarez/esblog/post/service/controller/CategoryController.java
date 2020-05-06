package dev.ericksuarez.esblog.post.service.controller;

import dev.ericksuarez.esblog.post.service.model.Category;
import dev.ericksuarez.esblog.post.service.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class CategoryController {

    private CategoryService categoryService;

    private final String CATEGORIES = "categories";

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(CATEGORIES + "/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long categoryId) {
        log.info("event=getCategoryByIdInvoked categoryId={}", categoryId);
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping(CATEGORIES)
    public List<Category> getAllCategories() {
        log.info("event=getAllCategoriesInvoked");
        return categoryService.findAllCategories();
    }

    @PostMapping(CATEGORIES)
    @ResponseStatus(HttpStatus.CREATED)
    public Category saveCategory(@Valid @RequestBody Category category) {
        log.info("event=saveCategoryInvoked category={}", category);
        return categoryService.saveCategory(category);
    }

    @PutMapping(CATEGORIES + "/{categoryId}")
    public Category updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody Category category) {
       log.info("event=updateCategoryInvoked categoryId={} category={]", categoryId, category);
       return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping(CATEGORIES + "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        log.info("event=deleteCategoryInvoked categoryId={}", categoryId);
        return categoryService.deleteCategory(categoryId);
    }
}
