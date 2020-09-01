package com.example.demo.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UpdatePasswordRequest {

    private String password;

    private String rePassword;
}
