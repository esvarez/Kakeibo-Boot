package dev.ericksuarez.esblog.post.service.controller;

import dev.ericksuarez.esblog.post.service.model.Category;
import dev.ericksuarez.esblog.post.service.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.PortUnreachableException;

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
        return categoryService.getCategoryById(categoryId);
    }
}
