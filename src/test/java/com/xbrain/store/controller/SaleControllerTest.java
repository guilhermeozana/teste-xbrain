package com.xbrain.store.controller;

import java.util.List;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.service.SaleService;
import com.xbrain.store.util.SaleRequestCreator;
import com.xbrain.store.util.SaleResponseCreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SaleControllerTest {
    @InjectMocks
    private SaleController saleController;
    @Mock
    private SaleService saleServiceMock;

    @BeforeEach
    void setUp(){

        BDDMockito.when(saleServiceMock.createSale(ArgumentMatchers.any(SaleRequest.class)))
                .thenReturn(SaleResponseCreator.createSaleResponse());

        BDDMockito.when(saleServiceMock.getAllSales())
                .thenReturn(List.of(SaleResponseCreator.createSaleResponse()));

        BDDMockito.when(saleServiceMock.getSaleById(ArgumentMatchers.anyLong()))
                .thenReturn(SaleResponseCreator.createSaleResponse());
        
        BDDMockito.doNothing().when(saleServiceMock).updateSale(ArgumentMatchers.any(SaleRequest.class));

        BDDMockito.doNothing().when(saleServiceMock).deleteSaleById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("createSale save sale and returns saleresponse when successful")
    void createSale_saveSaleAndReturnsSaleResponse_WhenSuccessful(){
        SaleResponse saleResponse = saleController.createSale(SaleRequestCreator.createSaleRequestToSaveSale()).getBody();
        
        Assertions.assertNotNull(saleResponse);
        Assertions.assertEquals(SaleResponseCreator.createSaleResponse() , saleResponse);
    }

    @Test
    @DisplayName("getAllSales returns list of saleresponse when successful")
    void getAllSales_ReturnsListOfSaleResponse_WhenSuccessful(){
        Double expectedValue = SaleResponseCreator.createSaleResponse().getValue();
        Long expectedSellerId = SaleResponseCreator.createSaleResponse().getSellerId();
        Long expectedId = SaleResponseCreator.createSaleResponse().getId();

        List<SaleResponse> sales = saleController.getAllSales().getBody();

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
        BDDMockito.when(saleServiceMock.getAllSales())
                .thenReturn(List.of());

        List<SaleResponse> sales = saleController.getAllSales().getBody();

        Assertions.assertNotNull(sales);
        Assertions.assertTrue(sales.isEmpty());
    }

    @Test
    @DisplayName("getSaleById returns saleresponse when successful")
    void getSaleById_returnsSaleResponse_WhenSuccessful(){
        Double expectedValue = SaleResponseCreator.createSaleResponse().getValue();
        Long expectedSellerId = SaleResponseCreator.createSaleResponse().getSellerId();
        Long expectedId = SaleResponseCreator.createSaleResponse().getId();

        SaleResponse sale = saleController.getSaleById(1L).getBody();

        Assertions.assertNotNull(sale);
        Assertions.assertEquals(expectedValue, sale.getValue());
        Assertions.assertEquals(expectedSellerId, sale.getSellerId());
        Assertions.assertEquals(expectedId, sale.getId());
    }

    @Test
    @DisplayName("updateSale updates sale when successful")
    void updateSale_updatesSale_whenSuccessful(){
        ResponseEntity<Void> entity = saleController.updateSale(SaleRequestCreator.createSaleRequestToUpdateSale());
        
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("deleteSaleById removes sale when successful")
    void deleteSaleById_removesSale_whenSuccessful(){
        ResponseEntity<Void> entity = saleController.deleteSaleById(1L);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }
}