package com.xbrain.store.util;

import java.time.LocalDate;

import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;

public class SaleCreator {

    public static Sale createSale(){
        return Sale.builder()
                .value(100.0)
                .seller(SellerCreator.createSeller())
                .date(LocalDate.now())
                .build();
    }

    public static Sale createSaleBySeller(Seller seller){
        return Sale.builder()
                .value(100.0)
                .seller(seller)
                .date(LocalDate.now())
                .build();
    }

    public static Sale createSaleWithoutSeller(){
        return Sale.builder()
                .value(100.0)
                .build();
    }

    public static Sale createSaleUpdated(){
        return Sale.builder()
                .id(1L)
                .value(200.0)
                .seller(SellerCreator.createSeller())
                .build();
    }
}
