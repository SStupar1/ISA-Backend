package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClinicResponse {

    private UUID id;

    private  String name;

    private String address;

    private String description;

    private String lat;

    private String lon;
}
