package hu.elte.inf.software.technology.bugtracker.application.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
    private static final String SECRET = "E783HJHWEW78232JH23278782323232JH42H42138127481274182412BJRJ21BR2131241";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    private static final String ROLE_KEY = "role";

    public static void addAuthentication(HttpServletResponse res, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_KEY, role);
        String JWT = Jwts.builder().setClaims(claims).setSubject(username).signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String user = null;
            String role = null;
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            user = claims.getSubject();
            role = (String) claims.get(ROLE_KEY);
            return user != null ? new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(role)) : null;
        }
        return null;
    }
}
