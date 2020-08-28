package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;

public interface ITokenProvider {

    String getUsernameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String generateToken(User user);
}