package com.hospitalMgt.patiencemgt.services;

import com.hospitalMgt.patiencemgt.dto.*;
import com.hospitalMgt.patiencemgt.entities.Token;
import com.hospitalMgt.patiencemgt.entities.TokenType;
import com.hospitalMgt.patiencemgt.entities.User;
import com.hospitalMgt.patiencemgt.exceptions.NotFoundException;
import com.hospitalMgt.patiencemgt.exceptions.UserAuthException;
import com.hospitalMgt.patiencemgt.exceptions.UserExistsException;
import com.hospitalMgt.patiencemgt.repositories.*;
import com.hospitalMgt.patiencemgt.utils.JwtUserDetailService;
import com.hospitalMgt.patiencemgt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final JwtUtil jwtUtil;

    private final JwtUserDetailService jwtUserDetailService;

    private final AuthenticationManager authenticationManager;

    private final PasswordResetTokenService passwordResetTokenService;

    private final ApplicationEventPublisher applicationEventPublisher;


    private final OtpRepository otpRepository;

    private final TokenRepository tokenRepository;


    @Transactional
    public AuthResponseDto userSignUp(SignupDto signupDto) throws UserExistsException {
        Optional<User> findUser = userRepository.findByEmail(signupDto.getEmail());
        if(findUser.isPresent()) throw new UserExistsException("User already exists");
        // create user in database
        User user = User.builder()
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .isEnabled(true)
                .contactNumber(signupDto.getPhoneNumber())
                .build();
        User createdUser = userRepository.save(user);

        return AuthResponseDto.builder()
                .message("User registered")
                .user(createdUser)
                .build();
    }

    public AuthResponseDto userSignIn(SignInDto signInDto) throws UserAuthException {
        String userEmail = signInDto.getEmail();
        String password = signInDto.getPassword();
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        if(findUser.isEmpty()) throw new UserAuthException("Invalid Credential, Try again");

        if(!findUser.get().isEnabled()) throw new UserAuthException("Account isn't verified");

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));
        if(!authenticate.isAuthenticated()) throw new UsernameNotFoundException("Bad Credentials, Try again");
        revokeUserTokens(findUser.get());
        return createJwt("User logged in successfully", findUser.get());
    }

    public AuthResponseDto createJwt(String message, User user) {
        String token = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        Token saveToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(saveToken);
        return AuthResponseDto.builder()
                .message(message)
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

    }



    public void revokeUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllUserTokens(user.getId());
        if(validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public ResponseData getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return ResponseData.builder()
                .message("All users")
                .data(allUsers)
                .build();
    }
}
