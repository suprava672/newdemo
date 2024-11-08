package com.example.newdemo.repository;

import com.example.newdemo.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemoReopository extends JpaRepository<Demo,Long> {
    Optional<Demo> findByEmail(String username);
}
