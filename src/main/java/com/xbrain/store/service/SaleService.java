package com.xbrain.store.service;

import java.util.List;
import java.util.stream.Collectors;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.exception.SaleNotFoundException;
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
    
    public SaleResponse createSale(SaleRequest saleRequest){
        
        Seller seller = sellerRepository.findById(saleRequest.getSellerId()).orElseThrow(() ->
                new SellerNotFoundException("No seller found with id "+ saleRequest.getSellerId()));
        Sale sale = SaleMapper.INSTANCE.mapDtoToSale(saleRequest, seller); 
        saleRepository.save(sale);
        return SaleMapper.INSTANCE.mapSaleToDto(sale, seller.getId()); 
    }

    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll().stream()
                .map((sale) -> SaleMapper.INSTANCE.mapSaleToDto(sale, sale.getSeller().getId()))
                .collect(Collectors.toList());
    }

    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> 
                new SaleNotFoundException("No sale found with id "+id));

        return SaleMapper.INSTANCE.mapSaleToDto(sale, sale.getSeller().getId());
    }

    public void updateSale(SaleRequest saleRequest) {
        Seller seller = sellerRepository.findById(saleRequest.getSellerId()).orElseThrow(() -> 
                new SellerNotFoundException("No seller found with id "+saleRequest.getSellerId()));

        saleRepository.save(SaleMapper.INSTANCE.mapDtoToSaleToUpdate(saleRequest, seller));  
    }

    public void deleteSaleById(Long id) {
        saleRepository.deleteById(id);
    }
}
