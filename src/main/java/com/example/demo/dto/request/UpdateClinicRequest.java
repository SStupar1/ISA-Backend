package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClinicRequest {

    private  String name;

    private String address;

    private String description;

    private String lat;

    private String lon;
}
