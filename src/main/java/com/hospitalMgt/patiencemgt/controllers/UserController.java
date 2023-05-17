package com.hospitalMgt.patiencemgt.controllers;

import com.hospitalMgt.patiencemgt.dto.AuthResponseDto;
import com.hospitalMgt.patiencemgt.dto.ResponseData;
import com.hospitalMgt.patiencemgt.dto.SignInDto;
import com.hospitalMgt.patiencemgt.dto.SignupDto;
import com.hospitalMgt.patiencemgt.exceptions.UserAuthException;
import com.hospitalMgt.patiencemgt.exceptions.UserExistsException;
import com.hospitalMgt.patiencemgt.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping(path = "/sign-up")
    public ResponseEntity<AuthResponseDto> userSignUp(@RequestBody @Valid SignupDto signupDto, HttpServletRequest request) throws UserExistsException, UserExistsException {
        AuthResponseDto responseDto =  userService.userSignUp(signupDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<AuthResponseDto> userLogin(@RequestBody @Valid SignInDto signInDto) throws UserAuthException {
        return ResponseEntity.ok(userService.userSignIn(signInDto));
    }

    // TODO: admin endpoint
    @GetMapping()
    public ResponseData getAllUsers() throws UserAuthException {
        return userService.getAllUsers();
    }
}
