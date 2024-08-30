package com.ecommerce_project.Ecommerce.security;

import com.ecommerce_project.Ecommerce.entities.Users;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;
    @Autowired
    public CustomUserDetailsService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        String roleName = user.getRole().getName();
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }
}
