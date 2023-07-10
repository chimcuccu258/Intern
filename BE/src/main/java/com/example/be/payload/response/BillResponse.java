package com.example.be.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {
    private LocalDateTime issue_date;
    private LocalDateTime expired_date;
    private Long total_price;
    private Long late_payment_fee;
    private Boolean isReturned;
    private Long userId;
}
