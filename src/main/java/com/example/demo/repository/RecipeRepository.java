package com.example.demo.repository;

import com.example.demo.entity.MedicalStaff;
import com.example.demo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    @Modifying
    @Transactional
    @Query("update Recipe r set r.nurse = :nurse  where r.id = :id")
    int updateUserStatus(@Param("nurse") MedicalStaff nurse, @Param("id") UUID id);
}

