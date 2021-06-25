package com.xbrain.store.mapper;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.model.Sale;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    @Mapping(target = "value", source = "saleDto.value")
    Sale mapDtoToSale(SaleRequest saleRequest);
}