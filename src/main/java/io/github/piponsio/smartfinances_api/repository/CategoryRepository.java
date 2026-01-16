package io.github.piponsio.smartfinances_api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.piponsio.smartfinances_api.entity.Category;



public interface CategoryRepository extends JpaRepository<Category,Long>{
    Optional<Category> findByName(String name);  
}
