package com.glenn.timekeeping_payroll.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceDto(
        String username,
        LocalDate workDate,
        LocalDateTime timeIn,
        LocalDateTime timeOut,
        long workedMinutes,
        double workedHours
) {}