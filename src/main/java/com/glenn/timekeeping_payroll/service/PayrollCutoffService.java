package com.glenn.timekeeping_payroll.service;

import com.glenn.timekeeping_payroll.dto.PayrollCutoffSummaryDto;
import com.glenn.timekeeping_payroll.entity.Attendance;
import com.glenn.timekeeping_payroll.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class PayrollCutoffService {

    private final AttendanceRepository attendanceRepo;
    private final AttendanceCalculator calculator;

    public PayrollCutoffService(AttendanceRepository attendanceRepo, AttendanceCalculator calculator) {
        this.attendanceRepo = attendanceRepo;
        this.calculator = calculator;
    }

    public CutoffRange resolveCutoff(LocalDate payrollDate) {
        int day = payrollDate.getDayOfMonth();

        if (day == 15) {
            // 27 prev month -> 10 current month
            LocalDate start = payrollDate.minusMonths(1).withDayOfMonth(27);
            LocalDate end = payrollDate.withDayOfMonth(10);
            return new CutoffRange(start, end);
        }

        if (day == 30) {
            // 11 -> 26 of same month
            LocalDate start = payrollDate.withDayOfMonth(11);
            LocalDate end = payrollDate.withDayOfMonth(26);
            return new CutoffRange(start, end);
        }

        throw new IllegalArgumentException("Payroll date must be 15 or 30.");
    }

    public List<PayrollCutoffSummaryDto> summarize(LocalDate payrollDate) {
        CutoffRange range = resolveCutoff(payrollDate);
        List<Attendance> rows = attendanceRepo.findAllByWorkDateBetween(range.start(), range.end());

        Map<String, Acc> map = new HashMap<>();

        for (Attendance a : rows) {
            String username = a.getUser().getUsername();
            map.putIfAbsent(username, new Acc());

            Acc acc = map.get(username);
            long mins = calculator.computeWorkedMinutes(a.getTimeIn(), a.getTimeOut());
            acc.totalMinutes += mins;

            if (a.getTimeIn() != null) acc.daysWithTimeIn++;
            if (a.getTimeOut() != null) acc.daysWithTimeOut++;
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new PayrollCutoffSummaryDto(
                        e.getKey(),
                        e.getValue().totalMinutes,
                        e.getValue().totalMinutes / 60.0,
                        e.getValue().daysWithTimeIn,
                        e.getValue().daysWithTimeOut
                ))
                .toList();
    }

    private static class Acc {
        long totalMinutes = 0;
        int daysWithTimeIn = 0;
        int daysWithTimeOut = 0;
    }

    public record CutoffRange(LocalDate start, LocalDate end) {}
}
