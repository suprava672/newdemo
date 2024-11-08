package com.example.newdemo.service;

import com.example.newdemo.entity.Demo;
import com.example.newdemo.repository.DemoReopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemoService {
    @Autowired
private PasswordEncoder passwordEncoder;
    @Autowired
   private DemoReopository demoReopository;

    // Create a new Demo record
    public Demo saveDemo(Demo demo) {
        demo.setPassword(passwordEncoder.encode(demo.getPassword()));

        return demoReopository.save(demo);
    }

    // Get all Demo records
    public List<Demo> getAllDemos() {
        return demoReopository.findAll();
    }

    // Get a Demo by ID
    public Optional<Demo> getDemoById(Long id) {
        return demoReopository.findById(id);
    }

    // Update a Demo record
    public Demo updateDemo(Long id, Demo updatedDemo) {
        return demoReopository.findById(id)
                .map(demo -> {
                    demo.setName(updatedDemo.getName());
                    demo.setEmail(updatedDemo.getEmail());
                    demo.setPassword(updatedDemo.getPassword());
                    demo.setRoles(updatedDemo.getRoles());
                    return demoReopository.save(demo);
                }).orElseThrow(() -> new RuntimeException("Demo not found with id " + id));
    }

    // Delete a Demo record
    public void deleteDemo(Long id) {
        demoReopository.deleteById(id);
    }
}
