package com.xbrain.store.util;

import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;

public class SaleCreator {

    public static Sale createSale(){
        return Sale.builder()
                .value(100.0)
                .seller(SellerCreator.createSeller())
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
