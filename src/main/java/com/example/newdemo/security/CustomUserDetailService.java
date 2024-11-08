package com.example.newdemo.security;

import com.example.newdemo.entity.Demo;
import com.example.newdemo.repository.DemoReopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private DemoReopository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Demo> email = userRepository.findByEmail(username);
        return email.map(CustomUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("user not found" + username));

    }
}
