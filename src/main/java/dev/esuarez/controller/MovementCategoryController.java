package dev.esuarez.controller;

import dev.esuarez.model.MovementCategory;
import dev.esuarez.service.MovementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static dev.esuarez.config.KakeiboUri.API;
import static dev.esuarez.config.KakeiboUri.MOVEMENT_CATEGORIES_API;

@RestController
@RequestMapping(API)
public class MovementCategoryController {

    @Autowired
    private MovementCategoryService movementCategoryService;

    @GetMapping(MOVEMENT_CATEGORIES_API)
    List<MovementCategory> getAllMovementCategories() {
        return movementCategoryService.getAllMovementCategories();
    }

    @GetMapping(MOVEMENT_CATEGORIES_API + "{id}")
    MovementCategory getMovementCategoryById (@PathVariable @Min(1) Long id){
        return movementCategoryService.findMovementCategoriesById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(MOVEMENT_CATEGORIES_API)
    MovementCategory createMovementCategory(@Valid @RequestBody MovementCategory movementCategory){
        return movementCategoryService.createMovementCategory(movementCategory);
    }

    @PutMapping(MOVEMENT_CATEGORIES_API + "/{id}")
    MovementCategory saveOrUpdateMovementCategory(@Valid @RequestBody MovementCategory movementCategory,
                                                  @PathVariable @Min(1) Long id){
        return movementCategoryService.saveOrUpdate(movementCategory, id);
    }

    @DeleteMapping(MOVEMENT_CATEGORIES_API + "{id}")
    ResponseEntity<?> deleteMovementCategory(@PathVariable @Min(1) Long id){
        return movementCategoryService.deleteMovementCategory(id);
    }

}
