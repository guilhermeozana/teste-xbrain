package com.xbrain.store.util;

import com.xbrain.store.model.Seller;

public class SellerCreator {

    public static Seller createSeller(){
        return Seller.builder()
                .id(1L)
                .name("Guilherme Campos")
                .build();
    }
}
