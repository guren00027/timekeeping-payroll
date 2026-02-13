package com.glenn.timekeeping_payroll.controller;

import com.glenn.timekeeping_payroll.entity.Attendance;
import com.glenn.timekeeping_payroll.service.AttendanceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/time-in")
    public Attendance timeIn(Authentication auth) {
        return attendanceService.timeIn(auth.getName());
    }

    @PostMapping("/time-out")
    public Attendance timeOut(Authentication auth) {
        return attendanceService.timeOut(auth.getName());
    }

    @GetMapping("/today")
    public Attendance today(Authentication auth) {
        return attendanceService.today(auth.getName());
    }
}
