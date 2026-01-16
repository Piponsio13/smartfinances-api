package io.github.piponsio.smartfinances_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.piponsio.smartfinances_api.entity.Category;
import io.github.piponsio.smartfinances_api.entity.User;
import io.github.piponsio.smartfinances_api.enums.type;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public void setDefaultCategories(User user){
        List<String> incomeNames = List.of("Salary", "Freelance", "Investments", "Other Income");
        List<String> expenseNames = List.of("Food & Dining", "Transportation", "Utilities", "Housing", 
                                            "Healthcare", "Shopping", "Education", "Subscriptions", 
                                            "Personal Care", "Other Expenses");
        
        List<Category> defaultCategories = createDefaultCategories(incomeNames, expenseNames);
        user.addCategories(defaultCategories);
    }

    private List<Category> createDefaultCategories(List<String> incomeNames, List<String> expenseNames){
        List<Category> categories = new ArrayList<>();
    
        incomeNames.forEach(name -> categories.add(createCategory(name, type.INCOME)));
        expenseNames.forEach(name -> categories.add(createCategory(name, type.EXPENSE)));
    
        return categories;
    }

    private Category createCategory(String name, type categoryType){
        Category category = new Category();
        category.setName(name);
        category.setType(categoryType);
        return category;
    }
}
