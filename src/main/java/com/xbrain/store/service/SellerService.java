package com.xbrain.store.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.exception.SellerNotFoundException;
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
        SellerResponse sellerResponse = SellerMapper.INSTANCE.mapSellerToDto(seller);
        sellerResponse.setTotalSales(0.0);
        sellerResponse.setAverageDailySales(0.0);
        return sellerResponse;
    }

    public List<SellerResponse> getAllSellers(){

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

    public List<SellerResponse> getSellersBySalesPerPeriod(String fromString, String toString) {

        LocalDate fromDate = LocalDate.parse(fromString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate toDate = LocalDate.parse(toString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<Seller> sellers = sellerRepository.findAll();

        return sellers.stream()
                .map(SellerMapper.INSTANCE::mapSellerToDto)
                .map((sellerResponse) -> {
                    List<Sale> salesPerPeriod = saleRepository.findBySellerIdAndDateBetween(sellerResponse.getId(), fromDate, toDate);
                    sellerResponse.setTotalSales(calculateTotalSales(salesPerPeriod));
                    sellerResponse.setAverageDailySales(calculateAverageDailySales(salesPerPeriod));
                    return sellerResponse;
                })
                .collect(Collectors.toList());
    }

    public SellerResponse getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> 
                new SellerNotFoundException("No seller found with id "+id));
        
        SellerResponse sellerResponse = SellerMapper.INSTANCE.mapSellerToDto(seller);
        List<Sale> sales = saleRepository.findAllBySellerId(sellerResponse.getId());

        sellerResponse.setTotalSales(calculateTotalSales(sales));
        sellerResponse.setAverageDailySales(calculateAverageDailySales(sales));

        return sellerResponse;
    }

    public void updateSeller(SellerRequest sellerRequest) {

        Seller seller = SellerMapper.INSTANCE.mapDtoToSellerToUpdate(sellerRequest);
        sellerRepository.save(seller);
    }

    public void deleteSellerById(Long id) {
        sellerRepository.deleteById(id);
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
