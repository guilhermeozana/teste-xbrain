package com.xbrain.store.util;

import com.xbrain.store.dto.SaleRequest;

public class SaleRequestCreator {

    public static SaleRequest createSaleRequestToSaveSale(){
        return SaleRequest.builder()
                .value(SaleResponseCreator.createSaleResponse().getValue())
                .sellerId(SaleResponseCreator.createSaleResponse().getSellerId())
                .build();
    }

    public static SaleRequest createSaleRequestToSaveSaleWithoutSellerId(){
        return SaleRequest.builder()
                .value(SaleResponseCreator.createSaleResponse().getValue())
                .build();
    }

    public static SaleRequest createSaleRequestToUpdateSale(){
        return SaleRequest.builder()
                .id(SaleCreator.createSale().getId())
                .value(200.0)
                .sellerId(1L)
                .build();
    }

    public static SaleRequest createSaleRequestToUpdateSaleWithoutSellerId(){
        return SaleRequest.builder()
                .id(SaleCreator.createSale().getId())
                .value(200.0)
                .build();
    }
}
