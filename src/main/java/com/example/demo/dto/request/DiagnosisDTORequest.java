package com.example.demo.dto.request;

import com.example.demo.entity.Diagnosis;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DiagnosisDTORequest {
    private UUID id;
    private String name;

    public DiagnosisDTORequest(UUID id,String name){
        this.id = id;
        this.name = name;
    }
    public DiagnosisDTORequest(){

    }
    public DiagnosisDTORequest(Diagnosis d){
        this(d.getId(),d.getName());
    }


}
