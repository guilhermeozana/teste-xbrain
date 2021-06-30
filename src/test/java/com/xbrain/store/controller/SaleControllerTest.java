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
    @DisplayName("createSale saves sale and returns saleresponse when successful")
    void createSale_savesSaleAndReturnsSaleResponse_WhenSuccessful(){
        ResponseEntity<SaleResponse> saleResponseEntity = saleController.createSale(SaleRequestCreator.createSaleRequestToSaveSale());
        
        Assertions.assertNotNull(saleResponseEntity.getBody());
        Assertions.assertEquals(SaleResponseCreator.createSaleResponse() , saleResponseEntity.getBody());
        Assertions.assertEquals(HttpStatus.CREATED , saleResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSales returns list of saleresponse when successful")
    void getAllSales_ReturnsListOfSaleResponse_WhenSuccessful(){
        Double expectedValue = SaleResponseCreator.createSaleResponse().getValue();
        Long expectedSellerId = SaleResponseCreator.createSaleResponse().getSellerId();
        Long expectedId = SaleResponseCreator.createSaleResponse().getId();

        ResponseEntity<List<SaleResponse>> salesResponseEntity = saleController.getAllSales();

        Assertions.assertNotNull(salesResponseEntity.getBody());
        Assertions.assertFalse(salesResponseEntity.getBody().isEmpty());
        Assertions.assertTrue(salesResponseEntity.getBody().size() == 1);
        Assertions.assertEquals(expectedValue, salesResponseEntity.getBody().get(0).getValue());
        Assertions.assertEquals(expectedSellerId, salesResponseEntity.getBody().get(0).getSellerId());
        Assertions.assertEquals(expectedId, salesResponseEntity.getBody().get(0).getId());
        Assertions.assertEquals(HttpStatus.OK, salesResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSales returns empty list when no sale is found")
    void getAllSales_ReturnsEmptyList_WhenNoSaleIsFound(){
        BDDMockito.when(saleServiceMock.getAllSales())
                .thenReturn(List.of());

        ResponseEntity<List<SaleResponse>> selesResponseEntity = saleController.getAllSales();

        Assertions.assertNotNull(selesResponseEntity.getBody());
        Assertions.assertTrue(selesResponseEntity.getBody().isEmpty());
        Assertions.assertEquals(HttpStatus.OK ,selesResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getSaleById returns saleresponse when successful")
    void getSaleById_returnsSaleResponse_WhenSuccessful(){
        Double expectedValue = SaleResponseCreator.createSaleResponse().getValue();
        Long expectedSellerId = SaleResponseCreator.createSaleResponse().getSellerId();
        Long expectedId = SaleResponseCreator.createSaleResponse().getId();

        ResponseEntity<SaleResponse> salesResponseEntity = saleController.getSaleById(1L);

        Assertions.assertNotNull(salesResponseEntity.getBody());
        Assertions.assertEquals(expectedValue, salesResponseEntity.getBody().getValue());
        Assertions.assertEquals(expectedSellerId, salesResponseEntity.getBody().getSellerId());
        Assertions.assertEquals(expectedId, salesResponseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.OK, salesResponseEntity.getStatusCode());
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