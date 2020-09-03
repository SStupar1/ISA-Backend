package com.example.demo.dto.response;

import com.example.demo.entity.Clinic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErResponse {

    private UUID id;

    private Long number;

    private String name;

//    private Clinic clinic;
}
