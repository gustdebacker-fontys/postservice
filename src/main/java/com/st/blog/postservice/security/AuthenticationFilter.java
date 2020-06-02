package com.st.blog.postservice.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationFilter extends OncePerRequestFilter {
    private TokenValidator tokenValidator;

    public AuthenticationFilter(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        var header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        var token = header.replace("Bearer ", "");

        try {
            var claims = tokenValidator.getClaims(token);

            if (claims.getSubject() != null && !claims.getSubject().equals("")) {
                @SuppressWarnings("unchecked") var authorities = ((List<LinkedHashMap<String, String>>) claims.get("authorities")).stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.get("role"))).collect(Collectors.toList());
                var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e) {
            // guarantee that the user won't be authenticated
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
