package com.xbrain.store.mapper;

import java.util.List;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.model.Seller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "sellerRequest.name")
    Seller mapDtoToSeller(SellerRequest sellerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "seller.name")
    SellerResponse mapSellerToDto(Seller seller);
}
