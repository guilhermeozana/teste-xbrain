package com.xbrain.store.service;

import java.util.List;
import java.util.Optional;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.exception.SaleNotFoundException;
import com.xbrain.store.exception.SellerNotFoundException;
import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;
import com.xbrain.store.repository.SaleRepository;
import com.xbrain.store.repository.SellerRepository;
import com.xbrain.store.util.SaleCreator;
import com.xbrain.store.util.SaleRequestCreator;
import com.xbrain.store.util.SaleResponseCreator;
import com.xbrain.store.util.SellerCreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SaleServiceTest {

    @Mock
    private SaleService saleService;
    @Mock
    private SaleRepository saleRepositoryMock;
    @Mock
    private SellerRepository sellerRepositoryMock;

    @BeforeEach
    void setUp(){

        BDDMockito.when(sellerRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(SellerCreator.createSeller()));

        BDDMockito.when(sellerRepositoryMock.save(ArgumentMatchers.any(Seller.class)))
                .thenReturn(SellerCreator.createSeller());

        BDDMockito.when(saleRepositoryMock.save(ArgumentMatchers.any(Sale.class)))
                .thenReturn(SaleCreator.createSale());

        BDDMockito.when(saleRepositoryMock.findAll())
                .thenReturn(List.of(SaleCreator.createSale()));

        BDDMockito.when(saleRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(SaleCreator.createSale()));
        
        BDDMockito.doNothing().when(saleService).updateSale(ArgumentMatchers.any(SaleRequest.class));

        BDDMockito.doNothing().when(saleService).deleteSaleById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("createSale saves sale and returns saleresponse when successful")
    void createSale_savesSaleAndReturnsSaleResponse_WhenSuccessful(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        SaleResponse saleResponse = saleService.createSale(SaleRequestCreator.createSaleRequestToSaveSale());
        System.out.println(saleResponse);
        Assertions.assertNotNull(saleResponse);
        Assertions.assertEquals(SaleResponseCreator.createSaleResponse() , saleResponse);
        Assertions.assertEquals(SaleResponseCreator.createSaleResponse().getValue() , saleResponse.getValue());
        Assertions.assertEquals(SaleResponseCreator.createSaleResponse().getSellerId() , saleResponse.getSellerId());
        Assertions.assertEquals(SaleResponseCreator.createSaleResponse().getId() , saleResponse.getId());
    }

    @Test
    @DisplayName("createSale throws SellerNotFoundException when no seller is found")
    void createSale_throwsSellerNotFoundException_whenNoSellerIsFound(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);
        
        Assertions.assertThrows(SellerNotFoundException.class, () -> saleService.createSale(SaleRequestCreator.createSaleRequestToSaveSaleWithoutSellerId()));
    }

    @Test
    @DisplayName("getAllSales returns list of saleresponse when successful")
    void getAllSales_ReturnsListOfSaleResponse_WhenSuccessful(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        Double expectedValue = SaleResponseCreator.createSaleResponse().getValue();
        Long expectedSellerId = SaleResponseCreator.createSaleResponse().getSellerId();
        Long expectedId = SaleResponseCreator.createSaleResponse().getId();

        List<SaleResponse> sales = saleService.getAllSales();

        Assertions.assertNotNull(sales);
        Assertions.assertFalse(sales.isEmpty());
        Assertions.assertTrue(sales.size() == 1);
        Assertions.assertEquals(expectedValue, sales.get(0).getValue());
        Assertions.assertEquals(expectedSellerId, sales.get(0).getSellerId());
        Assertions.assertEquals(expectedId, sales.get(0).getId());
    }

    @Test
    @DisplayName("getAllSales returns empty list when no sale is found")
    void getAllSales_ReturnsEmptyList_WhenNoSaleIsFound(){
        BDDMockito.when(saleRepositoryMock.findAll())
                .thenReturn(List.of());

        List<SaleResponse> sales = saleService.getAllSales();

        Assertions.assertNotNull(sales);
        Assertions.assertTrue(sales.isEmpty());
    }

    @Test
    @DisplayName("getSaleById returns saleresponse when successful")
    void getSaleById_returnsSaleResponse_WhenSuccessful(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        Double expectedValue = SaleResponseCreator.createSaleResponse().getValue();
        Long expectedSellerId = SaleResponseCreator.createSaleResponse().getSellerId();
        Long expectedId = SaleResponseCreator.createSaleResponse().getId();

        SaleResponse sale = saleService.getSaleById(1L);

        Assertions.assertNotNull(sale);
        Assertions.assertEquals(expectedValue, sale.getValue());
        Assertions.assertEquals(expectedSellerId, sale.getSellerId());
        Assertions.assertEquals(expectedId, sale.getId());
    }

    @Test
    @DisplayName("getSaleById throws SaleNotFoundException when no sale is found")
    void getSaleById_throwsSaleNotFoundException_whenNoSaleIsFound(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        BDDMockito.when(saleRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new SaleNotFoundException("No user found"));

        Assertions.assertThrows(SaleNotFoundException.class , () -> saleService.getSaleById(20L));
    }

    @Test
    @DisplayName("updateSale updates sale when successful")
    void updateSale_updatesSale_whenSuccessful(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        Assertions.assertDoesNotThrow(() -> saleService.updateSale(SaleRequestCreator.createSaleRequestToUpdateSale()));
    }

    @Test
    @DisplayName("updateSale throw SellerNotFoundException when no seller is found")
    void updateSale_throwsSellerNotFoundException_whenNoSellerIsFound(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        Assertions.assertThrows(SellerNotFoundException.class, () -> saleService.updateSale(SaleRequestCreator.createSaleRequestToSaveSaleWithoutSellerId()));
    }

    @Test
    @DisplayName("deleteSaleById removes sale when successful")
    void deleteSaleById_removesSale_whenSuccessful(){
        SaleService saleService = new SaleService(saleRepositoryMock, sellerRepositoryMock);

        Assertions.assertDoesNotThrow(() -> saleService.deleteSaleById(1L));
    }
}
