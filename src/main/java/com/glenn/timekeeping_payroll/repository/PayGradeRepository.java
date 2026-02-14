package com.glenn.timekeeping_payroll.repository;

import com.glenn.timekeeping_payroll.entity.PayGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayGradeRepository extends JpaRepository<PayGrade, Long> {
    Optional<PayGrade> findByCode(String code);
}
