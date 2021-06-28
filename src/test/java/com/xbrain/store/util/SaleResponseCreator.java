package com.xbrain.store.util;

import com.xbrain.store.dto.SaleResponse;

public class SaleResponseCreator {

    public static SaleResponse createSaleResponse(){
        return SaleResponse.builder()
                .value(100.0)
                .sellerId(1L)
                .build();
    }

    public static SaleResponse createUpdatedSaleResponse(){
        return SaleResponse.builder()
                .id(1L)
                .value(200.0)
                .sellerId(1L)
                .build();
    }
}
