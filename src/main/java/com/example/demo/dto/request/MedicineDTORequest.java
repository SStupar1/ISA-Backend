package com.example.demo.dto.request;

import com.example.demo.entity.Medicine;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MedicineDTORequest {
    private UUID id;
    private String name;

    public MedicineDTORequest(UUID id, String name){
        this.id = id;
        this.name = name;
    }
    public MedicineDTORequest(){

    }
    public MedicineDTORequest(Medicine m){
        this(m.getId(),m.getName());
    }

}

