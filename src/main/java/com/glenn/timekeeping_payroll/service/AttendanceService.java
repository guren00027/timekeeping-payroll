package com.glenn.timekeeping_payroll.service;

import com.glenn.timekeeping_payroll.entity.Attendance;
import com.glenn.timekeeping_payroll.entity.User;
import com.glenn.timekeeping_payroll.repository.AttendanceRepository;
import com.glenn.timekeeping_payroll.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final UserRepository userRepo;

    public AttendanceService(AttendanceRepository attendanceRepo, UserRepository userRepo) {
        this.attendanceRepo = attendanceRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Attendance timeIn(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        LocalDate today = LocalDate.now();

        Attendance att = attendanceRepo.findByUserAndWorkDate(user, today)
                .orElseGet(() -> {
                    Attendance a = new Attendance();
                    a.setUser(user);
                    a.setWorkDate(today);
                    return a;
                });

        if (att.getTimeIn() != null) {
            throw new IllegalStateException("Already timed in for today.");
        }

        att.setTimeIn(LocalDateTime.now());
        return attendanceRepo.save(att);
    }

    @Transactional
    public Attendance timeOut(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        LocalDate today = LocalDate.now();

        Attendance att = attendanceRepo.findByUserAndWorkDate(user, today)
                .orElseThrow(() -> new IllegalStateException("No attendance record for today."));

        if (att.getTimeIn() == null) {
            throw new IllegalStateException("Cannot time out without time in.");
        }

        if (att.getTimeOut() != null) {
            throw new IllegalStateException("Already timed out for today.");
        }

        att.setTimeOut(LocalDateTime.now());
        return attendanceRepo.save(att);
    }

    public Attendance today(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return attendanceRepo.findByUserAndWorkDate(user, LocalDate.now()).orElse(null);
    }
}
