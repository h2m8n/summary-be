package com.jj.summary.auth;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.summary.auth.token.Token;
import com.jj.summary.auth.token.TokenType;
import com.jj.summary.domain.User;
import com.jj.summary.dto.AuthenticationRequest;
import com.jj.summary.dto.AuthenticationResponse;
import com.jj.summary.dto.RegisterRequest;
import com.jj.summary.repository.TokenRepository;
import com.jj.summary.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .name(request.getName())
                .birth(request.getBirth())
                .phone(request.getPhone())
                .role(Role.USER)
                .build();
        

        try {
        	validateDuplicateUser(user);
        } catch (Exception e) {
        	return AuthenticationResponse.builder()
        			.accessToken(null)
        			.refreshToken(null)
        			.userInfo(null)
        			.build();
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        saveUserToken(savedUser, jwtToken);
        
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userInfo(user)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    	try {
    		authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
    	} catch (Exception e) {
    		return AuthenticationResponse.builder()
    				.accessToken(null)
    				.refreshToken(null)
    				.userInfo(null)
    				.build();
    	}
    	
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userInfo(user)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public String getUserPk(String token) {
        return jwtService.extractUsername(token);
    }
    public User getUserInfo(String token) {
    	String userName = getUserPk(token);
    	var user = repository.findByEmail(userName)
                .orElseThrow();

    	return user;
    }
    private void validateDuplicateUser(User user) {
    	repository.findByEmail(user.getEmail())
    		.ifPresent(m -> {
    			throw new IllegalStateException("이미 존재하는 회원입니다.");
    		});
    	repository.findByNickName(user.getNickName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 닉네임입니다.");
		});
    }
	public boolean validateDuplicateEmail(String email) {
		Optional<User> user = repository.findByEmail(email);
		
		if(user.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validateDuplicateNickName(String nickName) {
		Optional<User> user = repository.findByNickName(nickName);
		
		if(user.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
    	
}