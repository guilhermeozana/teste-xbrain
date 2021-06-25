package com.xbrain.store.mapper;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    @Mapping(target = "value", source = "saleRequest.value")
    @Mapping(target = "seller", source = "seller")
    Sale mapDtoToSale(SaleRequest saleRequest, Seller seller);

    @Mapping(target = "id", source = "sale.id")
    @Mapping(target = "value", source = "sale.value")
    @Mapping(target = "sellerId", source = "sellerId")
    SaleResponse mapSaleToDto(Sale sale, Long sellerId);
}