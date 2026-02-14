package com.glenn.timekeeping_payroll.config;

import com.glenn.timekeeping_payroll.entity.PayGrade;
import com.glenn.timekeeping_payroll.entity.PayType;
import com.glenn.timekeeping_payroll.entity.Role;
import com.glenn.timekeeping_payroll.entity.User;
import com.glenn.timekeeping_payroll.repository.PayGradeRepository;
import com.glenn.timekeeping_payroll.repository.RoleRepository;
import com.glenn.timekeeping_payroll.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepo,
                               UserRepository userRepo,
                               PayGradeRepository payGradeRepo,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            Role hrRole = roleRepo.findByName("ROLE_HR")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_HR")));

            Role employeeRole = roleRepo.findByName("ROLE_EMPLOYEE")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_EMPLOYEE")));

            // Seed Pay Grades
            PayGrade l1 = payGradeRepo.findByCode("L1")
                    .orElseGet(() -> payGradeRepo.save(new PayGrade(null, "L1", PayType.HOURLY, 100.0, null)));

            PayGrade l2 = payGradeRepo.findByCode("L2")
                    .orElseGet(() -> payGradeRepo.save(new PayGrade(null, "L2", PayType.HOURLY, 200.0, null)));

            PayGrade l3 = payGradeRepo.findByCode("L3")
                    .orElseGet(() -> payGradeRepo.save(new PayGrade(null, "L3", PayType.HOURLY, 300.0, null)));

            PayGrade head = payGradeRepo.findByCode("HEAD")
                    .orElseGet(() -> payGradeRepo.save(new PayGrade(null, "HEAD", PayType.MONTHLY, null, 60000.0)));


            // Create default HR user (only if not exists)
            userRepo.findByUsername("hr")
                    .orElseGet(() -> {
                        User hr = new User();
                        hr.setUsername("hr");
                        hr.setPassword(new BCryptPasswordEncoder().encode("Hr@12345"));
                        hr.setEnabled(true);
                        hr.setRoles(Set.of(hrRole));
                        hr.setPayGrade(head);
                        return userRepo.save(hr);
                    });

            // Create default EMPLOYEE user (only if not exists)
            userRepo.findByUsername("employee1")
                    .orElseGet(() -> {
                        User emp = new User();
                        emp.setUsername("employee1");
                        emp.setPassword(passwordEncoder.encode("Emp@12345"));
                        emp.setEnabled(true);
                        emp.setRoles(Set.of(employeeRole));
                        emp.setPayGrade(l1);
                        return userRepo.save(emp);
                    });
        };
    }
}
