package com.st.blog.postservice.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {
    private String jwtSecret = "0B25fKVB2Fcz8kOlVW5kxbsUiBricuw387pFv4fVUvJrxd4NlYae3ord018FACET";

    public Claims getClaims(String token) throws MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
