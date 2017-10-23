package hu.elte.inf.software.technology.bugtracker.application.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRoleController {
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUsersRole() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (!authorities.isEmpty()) {
            return authorities.iterator().next().getAuthority();
        } else {
            return "";
        }
    }
}
