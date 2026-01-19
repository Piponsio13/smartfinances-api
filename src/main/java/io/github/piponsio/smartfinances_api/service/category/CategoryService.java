package io.github.piponsio.smartfinances_api.service.category;

import io.github.piponsio.smartfinances_api.dto.request.CategoryRequestDto;
import io.github.piponsio.smartfinances_api.entity.User;

public interface CategoryService {
    void setDefaultCategory(User user);
    String createCustomCategory(CategoryRequestDto categoryRequestDto);
    void deleteCategory(String name);
}
