package com.glenn.timekeeping_payroll.repository;

import com.glenn.timekeeping_payroll.entity.Attendance;
import com.glenn.timekeeping_payroll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserAndWorkDate(User user, LocalDate workDate);
    List<Attendance> findAllByWorkDate(LocalDate workDate);
}

