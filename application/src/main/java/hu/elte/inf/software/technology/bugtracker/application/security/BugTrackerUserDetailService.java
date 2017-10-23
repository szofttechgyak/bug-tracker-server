package hu.elte.inf.software.technology.bugtracker.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.service.UserService;

@Service
public class BugTrackerUserDetailService implements UserDetailsService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        hu.elte.inf.software.technology.bugtracker.domain.User user = userService.getUserByUserName(username);
        if (user == null) {
            return null;
        } else {
            return new User(user.getUserName(), user.getPassword(),
                     AuthorityUtils.createAuthorityList(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_GENERAL"));
        }
    }
    
}