package com.nt.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nt.entity.Admin;
import com.nt.repo.AdminRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {
        try {
            if (adminRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Admin admin = new Admin();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                adminRepository.save(admin);
                log.info("Admin account created.");
            } 
        } catch (Exception e) {
            log.error("Failed to initialize admin user. MongoDB may not be reachable.", e);
        }
    }
}
