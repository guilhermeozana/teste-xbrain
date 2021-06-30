package com.xbrain.store.util;

import com.xbrain.store.dto.SellerRequest;

public class SellerRequestCreator {

    public static SellerRequest createSellerRequestToSaveSeller(){
        return SellerRequest.builder()
                .name(SellerResponseCreator.createSellerResponse().getName())
                .build();
    }

    public static SellerRequest createSellerRequestToUpdateSeller(){
        return SellerRequest.builder()
                .id(SellerCreator.createSeller().getId())
                .name("Guilherme Ozana")
                .build();
    }
}
