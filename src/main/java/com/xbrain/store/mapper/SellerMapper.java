package com.xbrain.store.mapper;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.model.Seller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface SellerMapper {

    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "sellerRequest.name")
    Seller mapDtoToSeller(SellerRequest sellerRequest);

    @Mapping(target = "id", source = "sellerRequest.id")
    @Mapping(target = "name", source = "sellerRequest.name")
    Seller mapDtoToSellerToUpdate(SellerRequest sellerRequest);

    @Mapping(target = "id", source = "seller.id")
    @Mapping(target = "name", source = "seller.name")
    SellerResponse mapSellerToDto(Seller seller);
}
