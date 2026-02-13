package com.glenn.timekeeping_payroll.controller;

import com.glenn.timekeeping_payroll.entity.Attendance;
import com.glenn.timekeeping_payroll.repository.AttendanceRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hr/attendance")
public class HrAttendanceController {

    private final AttendanceRepository attendanceRepo;

    public HrAttendanceController(AttendanceRepository attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    @GetMapping("/by-date")
    public List<Attendance> byDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceRepo.findAllByWorkDate(date);
    }
}
