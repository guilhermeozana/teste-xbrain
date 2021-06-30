package com.xbrain.store.util;

import com.xbrain.store.dto.SellerResponse;

public class SellerResponseCreator {
    public static SellerResponse createSellerResponse(){
        return SellerResponse.builder()
                .id(1L)
                .name("Guilherme Campos")
                .totalSales(0.0)
                .averageDailySales(0.0)
                .build();
    }

    public static SellerResponse createUpdatedSellerResponse(){
        return SellerResponse.builder()
                .id(1L)
                .name("Guilherme Ozana")
                .build();
    }
}
