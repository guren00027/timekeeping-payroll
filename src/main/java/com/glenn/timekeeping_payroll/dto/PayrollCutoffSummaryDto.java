package com.glenn.timekeeping_payroll.dto;

public record PayrollCutoffSummaryDto(
        String username,
        String payGrade,
        String payType,
        double rate,              // hourlyRate if HOURLY, monthlyRate if MONTHLY
        long totalWorkedMinutes,
        double totalWorkedHours,
        int daysWithTimeIn,
        int daysWithTimeOut,
        double regularPay
) {}

