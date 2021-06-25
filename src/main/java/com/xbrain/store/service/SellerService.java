package com.xbrain.store.service;

import java.util.List;
import java.util.stream.Collectors;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.mapper.SellerMapper;
import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;
import com.xbrain.store.repository.SaleRepository;
import com.xbrain.store.repository.SellerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final SaleRepository saleRepository;

    public SellerResponse createSeller(SellerRequest sellerRequest){
        Seller seller = sellerRepository.save(SellerMapper.INSTANCE.mapDtoToSeller(sellerRequest));
        return SellerMapper.INSTANCE.mapSellerToDto(seller);
    }

    public List<SellerResponse> getSellers(){

        return sellerRepository.findAll()
                .stream()
                .map(SellerMapper.INSTANCE::mapSellerToDto)
                .map((sellerResponse) -> {
                    List<Sale> sales = saleRepository.findAllBySellerId(sellerResponse.getId());
                    sellerResponse.setTotalSales(calculateTotalSales(sales));
                    sellerResponse.setAverageDailySales(calculateAverageDailySales(sales));
                    return sellerResponse;
                })
                .collect(Collectors.toList());
    }

    public Double calculateTotalSales(List<Sale> sales){
        Double totalSales = 0.0;
        for(Sale sale : sales){
            totalSales += sale.getValue();
        }
        return totalSales;
    }

    public Double calculateAverageDailySales(List<Sale> sales){
        if(sales.isEmpty()){
            return 0.0;
        }
        return calculateTotalSales(sales) / sales.size();
    }

}
