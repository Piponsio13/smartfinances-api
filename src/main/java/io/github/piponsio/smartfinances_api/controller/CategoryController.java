package io.github.piponsio.smartfinances_api.controller;

import java.util.List;

import javax.print.DocFlavor.STRING;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.piponsio.smartfinances_api.dto.request.CategoryRequestDto;
import io.github.piponsio.smartfinances_api.dto.response.CategoryResponseDto;
import io.github.piponsio.smartfinances_api.service.category.CategoryService;
import io.github.piponsio.smartfinances_api.utils.CustomResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private static final String ALL_CATEGORIES_SUCCESFULL = "All user categories retrieved succesfully";

    private static final String ALL_CATEGORIES_NOT_FOUND = "User does not have categories";

    @PostMapping("/create")
    ResponseEntity<CustomResponse<String>> createCustomCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        String categoryName = categoryService.createCustomCategory(categoryRequestDto);
        CustomResponse<String> customResponse = CustomResponse.<String>builder()
                .data(categoryName)
                .message("Category created succesfully")
                .statusCode(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(customResponse);
    }

    @DeleteMapping("/delete/{name}")
    ResponseEntity<CustomResponse<Void>> deleteCustomCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        CustomResponse<Void> customResponse = CustomResponse.<Void>builder()
                .data(null)
                .message("Category deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity.status(HttpStatus.OK.value()).body(customResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<CategoryResponseDto>>> getAllUserCategories() {
        List<CategoryResponseDto> response = categoryService.getAllUserCategories();

        HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT;

        CustomResponse<List<CategoryResponseDto>> customResponse = CustomResponse.<List<CategoryResponseDto>>builder()
                .data(response)
                .message(response != null ? ALL_CATEGORIES_SUCCESFULL : ALL_CATEGORIES_NOT_FOUND)
                .statusCode(status.value())
                .build();

        return ResponseEntity.status(status.value()).body(customResponse);
    }
}
