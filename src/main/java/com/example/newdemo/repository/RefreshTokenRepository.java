package com.example.newdemo.repository;

import com.example.newdemo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken,Integer>{
    Optional<RefreshToken> findByToken(String  token);
}

