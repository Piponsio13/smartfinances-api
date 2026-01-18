package io.github.piponsio.smartfinances_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.piponsio.smartfinances_api.dto.request.CategoryRequestDto;
import io.github.piponsio.smartfinances_api.service.category.CategoryService;
import io.github.piponsio.smartfinances_api.utils.CustomResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    
    @PostMapping("/create")
    ResponseEntity<CustomResponse<String>> createCustomCategory(@RequestBody CategoryRequestDto categoryRequestDto) throws Exception{
        String categoryName = categoryService.createCustomCategory(categoryRequestDto);
        CustomResponse<String> customResponse = CustomResponse.<String>builder()
            .data(categoryName)
            .message("Category created succesfully")
            .statusCode(HttpStatus.CREATED.value())
            .build();

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(customResponse);
    }

    @DeleteMapping("/delete/{name}")
    ResponseEntity<CustomResponse<Void>> deleteCustomCategory(@PathVariable String name){
        categoryService.deleteCategory(name);
        CustomResponse<Void> customResponse = CustomResponse.<Void>builder()
            .data(null)
            .message("Category deleted successfully")
            .statusCode(HttpStatus.OK.value())
            .build();
        
            return ResponseEntity.status(HttpStatus.OK.value()).body(customResponse);
    }
}
