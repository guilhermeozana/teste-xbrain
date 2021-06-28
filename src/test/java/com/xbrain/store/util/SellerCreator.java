package com.xbrain.store.util;

import java.util.List;

import com.xbrain.store.model.Seller;

public class SellerCreator {

    public static Seller createSeller(){
        return Seller.builder()
                .id(1L)
                .name("Guilherme Campos")
                .build();
    }
}
