package com.example.newdemo.controller;

import com.example.newdemo.entity.Demo;
import com.example.newdemo.entity.RefreshToken;
import com.example.newdemo.security.JwtService;
import com.example.newdemo.service.DemoService;
import com.example.newdemo.service.RefreshTokenService;
import com.example.newdemo.util.LoginRequest;
import com.example.newdemo.util.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demos")
public class DemoController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    // Create a new Demo
    @PostMapping("/create")
    public ResponseEntity<Demo> createDemo(@RequestBody Demo demo) {
        Demo savedDemo = demoService.saveDemo(demo);
        return ResponseEntity.ok(savedDemo);
    }

    // Get all Demos
    @GetMapping
    public ResponseEntity<List<Demo>> getAllDemos() {
        List<Demo> demos = demoService.getAllDemos();
        return ResponseEntity.ok(demos);
    }

    // Get a Demo by ID
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Demo> getDemoById(@PathVariable Long id) {
        Optional<Demo> demo = demoService.getDemoById(id);
        return demo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a Demo
    @PutMapping("/{id}")
    public ResponseEntity<Demo> updateDemo(@PathVariable Long id, @RequestBody Demo demo) {
        Demo updatedDemo = demoService.updateDemo(id, demo);
        return ResponseEntity.ok(updatedDemo);
    }
    // Delete a Demo
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDemo(@PathVariable Long id) {
        demoService.deleteDemo(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

            return LoginResponse.builder()
                    .accessToken(jwtService.generateToken(loginRequest.getUsername()))
                    .jwtToken(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user");
        }

    }
}
