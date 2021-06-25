package com.xbrain.store.service;

import java.util.Optional;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.exception.SellerNotFoundException;
import com.xbrain.store.mapper.SaleMapper;
import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;
import com.xbrain.store.repository.SaleRepository;
import com.xbrain.store.repository.SellerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final SellerRepository sellerRepository;
    private final SaleMapper saleMapper;
    
    public Sale createSale(SaleRequest saleRequest){
        sellerRepository.findById(saleRequest.getSellerId()).orElseThrow(() ->
                new SellerNotFoundException("No seller found with id "+ saleRequest.getSellerId()));
        Sale sale = saleMapper.mapDtoToSale(saleRequest);
                
        return saleRepository.save(sale);
    }
}
