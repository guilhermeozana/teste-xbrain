package com.xbrain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponse {
    private Long id;
    private String name;
    private Double totalSales;
    private Double averageDailySales;
}