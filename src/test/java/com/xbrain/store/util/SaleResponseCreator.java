package com.xbrain.store.util;

import java.time.LocalDate;

import com.xbrain.store.dto.SaleResponse;

public class SaleResponseCreator {

    public static SaleResponse createSaleResponse(){
        return SaleResponse.builder()
                .value(100.0)
                .date(LocalDate.now())
                .sellerId(1L)
                .build();
    }

    public static SaleResponse createUpdatedSaleResponse(){
        return SaleResponse.builder()
                .id(1L)
                .date(LocalDate.now())
                .value(200.0)
                .sellerId(1L)
                .build();
    }
}
