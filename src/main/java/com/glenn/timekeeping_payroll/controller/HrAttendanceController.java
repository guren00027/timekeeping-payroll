package com.glenn.timekeeping_payroll.controller;

import com.glenn.timekeeping_payroll.dto.AttendanceDto;
import com.glenn.timekeeping_payroll.entity.Attendance;
import com.glenn.timekeeping_payroll.repository.AttendanceRepository;
import com.glenn.timekeeping_payroll.service.AttendanceCalculator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hr/attendance")
public class HrAttendanceController {

    private final AttendanceRepository attendanceRepo;
    private final AttendanceCalculator calculator;

    public HrAttendanceController(AttendanceRepository attendanceRepo, AttendanceCalculator calculator) {
        this.attendanceRepo = attendanceRepo;
        this.calculator = calculator;
    }

    @GetMapping("/by-date")
    public List<AttendanceDto> byDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceRepo.findAllByWorkDate(date).stream()
                .map(this::toDto)
                .toList();
    }

    private AttendanceDto toDto(Attendance a) {
        long workedMinutes = calculator.computeWorkedMinutes(a.getTimeIn(), a.getTimeOut());
        double workedHours = workedMinutes / 60.0;

        return new AttendanceDto(
                a.getUser().getUsername(),
                a.getWorkDate(),
                a.getTimeIn(),
                a.getTimeOut(),
                workedMinutes,
                workedHours
        );
    }
}