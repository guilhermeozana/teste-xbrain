package com.xbrain.store.controller;


import java.time.LocalDate;
import java.util.List;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.service.SellerService;
import com.xbrain.store.util.SellerRequestCreator;
import com.xbrain.store.util.SellerResponseCreator;

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
public class SellerControllerTest {
    @InjectMocks
    private SellerController sellerController;
    @Mock
    private SellerService sellerServiceMock;

    @BeforeEach
    void setUp(){

        BDDMockito.when(sellerServiceMock.createSeller(ArgumentMatchers.any(SellerRequest.class)))
                .thenReturn(SellerResponseCreator.createSellerResponse());

        BDDMockito.when(sellerServiceMock.getAllSellers())
                .thenReturn(List.of(SellerResponseCreator.createSellerResponse()));

        BDDMockito.when(sellerServiceMock.getSellersBySalesPerPeriod(LocalDate.now().toString(),LocalDate.now().toString()))
                .thenReturn(List.of(SellerResponseCreator.createSellerResponse()));

        BDDMockito.when(sellerServiceMock.getSellerById(ArgumentMatchers.anyLong()))
                .thenReturn(SellerResponseCreator.createSellerResponse());

        BDDMockito.doNothing().when(sellerServiceMock).updateSeller(ArgumentMatchers.any(SellerRequest.class));

        BDDMockito.doNothing().when(sellerServiceMock).deleteSellerById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("createSeller returns sellerresponse when successful")
    void createSeller_returnsSellerResponse_whenSuccessful(){

        SellerResponse expectedSeller = SellerResponseCreator.createSellerResponse();

        ResponseEntity<SellerResponse> sellersResponseEntity = sellerController.createSeller(SellerRequestCreator.createSellerRequestToSaveSeller());
    
        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertEquals(expectedSeller, sellersResponseEntity.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, sellersResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSellers returns list of sellerresponse when successful")
    void getAllSellers_returnsListOfSellerResponse_whenSuccessful(){
        String expectedName = SellerResponseCreator.createSellerResponse().getName();
        Long expectedId = SellerResponseCreator.createSellerResponse().getId();

        ResponseEntity<List<SellerResponse>> sellersResponseEntity = sellerController.getAllSellers();

        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertFalse(sellersResponseEntity.getBody().isEmpty());
        Assertions.assertTrue(sellersResponseEntity.getBody().size() == 1);
        Assertions.assertEquals(expectedName, sellersResponseEntity.getBody().get(0).getName());
        Assertions.assertEquals(expectedId, sellersResponseEntity.getBody().get(0).getId());
        Assertions.assertEquals(HttpStatus.OK, sellersResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSellers returns empty list when no seller is found")
    void getAllSellers_ReturnsEmptyList_WhenNoSellerIsFound(){
        BDDMockito.when(sellerServiceMock.getAllSellers())
                .thenReturn(List.of());

        ResponseEntity<List<SellerResponse>> sellersResponseEntity = sellerController.getAllSellers();

        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertTrue(sellersResponseEntity.getBody().isEmpty());
        Assertions.assertEquals(HttpStatus.OK ,sellersResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSellersBySalesPerPeriod returns all sellers by specific sales period when successful")
    void getAllSellersBySalesPerPeriod_returnsAllSellersBySpecificSalesPeriod_whenSuccessful(){
        String expectedName = SellerResponseCreator.createSellerResponse().getName();
        Long expectedId = SellerResponseCreator.createSellerResponse().getId();
        
        ResponseEntity<List<SellerResponse>> sellersResponseEntityByPeriod = sellerController.getAllSellersBySalesPerPeriod(LocalDate.now().toString(),LocalDate.now().toString());

        Assertions.assertNotNull(sellersResponseEntityByPeriod.getBody());
        Assertions.assertFalse(sellersResponseEntityByPeriod.getBody().isEmpty());
        Assertions.assertTrue(sellersResponseEntityByPeriod.getBody().size() == 1);
        Assertions.assertEquals(expectedName, sellersResponseEntityByPeriod.getBody().get(0).getName());
        Assertions.assertEquals(expectedId, sellersResponseEntityByPeriod.getBody().get(0).getId());
        Assertions.assertEquals(HttpStatus.OK, sellersResponseEntityByPeriod.getStatusCode());
    }

    @Test
    @DisplayName("getAllSellersBySalesPerPeriod returns empty list when no seller is found")
    void getAllSellersBySalesPerPeriod_ReturnsEmptyList_WhenNoSellerIsFound(){
        BDDMockito.when(sellerServiceMock.getSellersBySalesPerPeriod(LocalDate.now().toString(),LocalDate.now().toString()))
                .thenReturn(List.of());

        ResponseEntity<List<SellerResponse>> sellersResponseEntity = sellerController.getAllSellersBySalesPerPeriod(LocalDate.now().toString(),LocalDate.now().toString());

        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertTrue(sellersResponseEntity.getBody().isEmpty());
        Assertions.assertEquals(HttpStatus.OK, sellersResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getSellerById returns sellerresponse when successful")
    void getSellerById_returnsSellerResponse_whenSuccessful(){
        String expectedName = SellerResponseCreator.createSellerResponse().getName();
        Long expectedId = SellerResponseCreator.createSellerResponse().getId();

        ResponseEntity<SellerResponse> sellersResponseEntity = sellerController.getSellerById(1L);

        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertEquals(expectedName, sellersResponseEntity.getBody().getName());
        Assertions.assertEquals(expectedId, sellersResponseEntity.getBody().getId());

    }

    @Test
    @DisplayName("updateSeller updates seller when successful")
    void updateSeller_updatesSeller_whenSuccessful(){
        ResponseEntity<Void> entity = sellerController.updateSeller(SellerRequestCreator.createSellerRequestToUpdateSeller());
        
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("deleteSellerById removes seller when successful")
    void deleteSaleById_removesSale_whenSuccessful(){
        ResponseEntity<Void> entity = sellerController.deleteSellerById(1L);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }
}
