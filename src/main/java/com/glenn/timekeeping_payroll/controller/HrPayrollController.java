package com.glenn.timekeeping_payroll.controller;

import com.glenn.timekeeping_payroll.dto.PayrollCutoffSummaryDto;
import com.glenn.timekeeping_payroll.service.PayrollCutoffService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hr/payroll")
public class HrPayrollController {

    private final PayrollCutoffService cutoffService;

    public HrPayrollController(PayrollCutoffService cutoffService) {
        this.cutoffService = cutoffService;
    }

    @GetMapping("/cutoff-summary")
    public List<PayrollCutoffSummaryDto> cutoffSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate payrollDate
    ) {
        return cutoffService.summarize(payrollDate);
    }
}
