package com.glenn.timekeeping_payroll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pay_grades")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PayGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // L1, L2, L3, HEAD

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayType payType; // HOURLY or MONTHLY

    // Only used when payType = HOURLY
    private Double hourlyRate;

    // Only used when payType = MONTHLY
    private Double monthlyRate;
}
