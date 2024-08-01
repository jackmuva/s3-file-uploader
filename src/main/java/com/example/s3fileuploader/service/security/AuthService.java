package com.example.s3fileuploader.service.security;


import com.example.s3fileuploader.model.security.LoginDTO;
import com.example.s3fileuploader.model.security.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDTO loginDto);
    ResponseEntity<String> register(RegisterDTO registerDTO) throws Exception;
}
