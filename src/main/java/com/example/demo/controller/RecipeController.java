package com.example.demo.controller;

import com.example.demo.dto.request.RecipeDTORequest;
import com.example.demo.dto.response.RecipeDTOResponse;
import com.example.demo.entity.Recipe;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.impl.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/recipe",produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(value = "/updateRecipe",method = RequestMethod.POST)
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> updateRecipe(@RequestBody RecipeDTORequest recipeDTORequest){
        int rows = recipeService.updateRecipe(recipeDTORequest.getNurse(),recipeDTORequest.getRecipeId());
        return new ResponseEntity<>(rows, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value= "/getAllPending",method = RequestMethod.GET)
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> getAllPending(){
        List<Recipe> recipeList = recipeService.findAll();
        List<RecipeDTOResponse> recipeDTOResponses = new ArrayList<>();
        for(Recipe r : recipeList){
            if(r.getNurse() == null){
                recipeDTOResponses.add(new RecipeDTOResponse(r.getPatient().getUser().getFirstName() +  ' '  + r.getPatient().getUser().getLastName(),
                        null,
                        r.getMedicine(),
                        r.getId()));
            }
        }
        return new ResponseEntity<>(recipeDTOResponses,HttpStatus.OK);

    }
}

