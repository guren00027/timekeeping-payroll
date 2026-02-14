package com.glenn.timekeeping_payroll.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AttendanceCalculator {

    // Rule B: subtract 60 minutes break if shift >= 5 hours
    public long computeWorkedMinutes(LocalDateTime timeIn, LocalDateTime timeOut) {
        if (timeIn == null || timeOut == null) return 0;

        long minutes = Duration.between(timeIn, timeOut).toMinutes();
        if (minutes < 0) return 0;

        if (minutes >= 5 * 60) {
            minutes -= 60;
        }
        return Math.max(minutes, 0);
    }
}
