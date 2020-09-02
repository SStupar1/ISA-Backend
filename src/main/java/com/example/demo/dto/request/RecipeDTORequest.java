package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecipeDTORequest {

    private UUID nurse;
    private UUID recipeId;
}
