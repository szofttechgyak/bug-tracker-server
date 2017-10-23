package hu.elte.inf.software.technology.bugtracker.application.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserDetailsService userDetails;

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserDetailsService userDetails) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userDetails = userDetails;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
        UserDetails user = userDetails.loadUserByUsername(creds.getUsername());
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),
                user == null ? Collections.emptyList() : user.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        UserDetails user = userDetails.loadUserByUsername(auth.getName());
        String role = user.getAuthorities().iterator().next().getAuthority();
        TokenAuthenticationService.addAuthentication(res, auth.getName(), role);
    }
}
