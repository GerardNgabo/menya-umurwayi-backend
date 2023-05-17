package com.hospitalMgt.patiencemgt.config;

import com.hospitalMgt.patiencemgt.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutConfig implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader("Authorization");
        String jwtToken = null;
        // check if header has keyword 'bearer' in it
        if(header == null || !header.startsWith("Bearer ")) {
            return;
        }
        jwtToken = header.split(" ")[1];
        var storedToken = tokenRepository.findByToken(jwtToken)
                .orElse(null);

        if(storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}
