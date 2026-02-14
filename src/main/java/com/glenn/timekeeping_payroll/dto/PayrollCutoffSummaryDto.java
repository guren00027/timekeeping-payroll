package com.glenn.timekeeping_payroll.dto;

public record PayrollCutoffSummaryDto(
        String username,
        long totalWorkedMinutes,
        double totalWorkedHours,
        int daysWithTimeIn,
        int daysWithTimeOut
) {}

