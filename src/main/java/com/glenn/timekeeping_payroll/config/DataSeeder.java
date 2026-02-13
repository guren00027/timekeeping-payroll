package com.glenn.timekeeping_payroll.config;

import com.glenn.timekeeping_payroll.entity.Role;
import com.glenn.timekeeping_payroll.entity.User;
import com.glenn.timekeeping_payroll.repository.RoleRepository;
import com.glenn.timekeeping_payroll.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepo, UserRepository userRepo) {
        return args -> {
            Role hrRole = roleRepo.findByName("ROLE_HR")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_HR")));

            Role employeeRole = roleRepo.findByName("ROLE_EMPLOYEE")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_EMPLOYEE")));

            // Create default HR user (only if not exists)
            userRepo.findByUsername("hr")
                    .orElseGet(() -> {
                        User hr = new User();
                        hr.setUsername("hr");
                        hr.setPassword(new BCryptPasswordEncoder().encode("Hr@12345"));
                        hr.setEnabled(true);
                        hr.setRoles(Set.of(hrRole));
                        return userRepo.save(hr);
                    });
        };
    }
}
