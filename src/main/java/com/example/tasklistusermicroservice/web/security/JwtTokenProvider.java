package com.example.tasklistusermicroservice.web.security;

import com.example.tasklistusermicroservice.model.user.Role;
import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.service.impl.UserServiceImpl;
import com.example.tasklistusermicroservice.service.properties.JwtProperties;
import com.example.tasklistusermicroservice.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final JwtUserDetailsService userDetailsService;

    private final UserServiceImpl userService;

    private Key key;

    @Autowired
    public JwtTokenProvider(JwtProperties jwtProperties, JwtUserDetailsService userDetailsService, UserServiceImpl userService) {
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException("Refresh token not valid");
        }
        Long id = Long.valueOf(getId(refreshToken));
        User user = userService.getById(id);
        String email = user.getEmail();
        jwtResponse.setId(id);
        jwtResponse.setUsername(email);
        jwtResponse.setAccessToken(createAccessToken(id, email, user.getRole()));
        jwtResponse.setRefreshToken(createRefreshToken(id, email));
        return jwtResponse;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token){
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }
}
