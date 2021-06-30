package com.xbrain.store.integration;

import java.util.List;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.model.Sale;
import com.xbrain.store.repository.SaleRepository;
import com.xbrain.store.repository.SellerRepository;
import com.xbrain.store.util.SaleCreator;
import com.xbrain.store.util.SaleRequestCreator;
import com.xbrain.store.util.SellerCreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SaleControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    @DisplayName("createSale returns saleresponse when successful")
    void createSale_savesSaleAndReturnsSaleresponse_whenSuccessful(){
        SaleRequest saleRequest = SaleRequestCreator.createSaleRequestToSaveSale();
        sellerRepository.save(SellerCreator.createSeller());
        

        ResponseEntity<SaleResponse> saleResponseEntity = testRestTemplate.postForEntity("/api/sales", saleRequest, SaleResponse.class);

        Assertions.assertNotNull(saleResponseEntity);
        Assertions.assertNotNull(saleResponseEntity.getBody());
        Assertions.assertNotNull(saleResponseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED, saleResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSales returns list of saleresponse when successful")
    void getAllSales_returnsListOfSaleresponse_whenSuccessful(){
        sellerRepository.save(SellerCreator.createSeller());
        Sale expectedSale = saleRepository.save(SaleCreator.createSale());

        ResponseEntity<List<SaleResponse>> salesResponseEntity = testRestTemplate.exchange("/api/sales/all", HttpMethod.GET, null, 
                new ParameterizedTypeReference<List<SaleResponse>>(){});

        Assertions.assertNotNull(salesResponseEntity.getBody());
        Assertions.assertFalse(salesResponseEntity.getBody().isEmpty());
        Assertions.assertTrue(salesResponseEntity.getBody().size() == 1);
        Assertions.assertEquals(expectedSale.getValue(), salesResponseEntity.getBody().get(0).getValue());
        Assertions.assertEquals(expectedSale.getId(), salesResponseEntity.getBody().get(0).getId());
    }

    @Test
    @DisplayName("getAllSales returns empty list when no sale is found")
    void getAllSales_returnsEmptyList_whenNoSaleIsFound(){
        sellerRepository.save(SellerCreator.createSeller());

        ResponseEntity<List<SaleResponse>> saleResponseEntity = testRestTemplate.exchange("/api/sales/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SaleResponse>>(){});

        Assertions.assertNotNull(saleResponseEntity.getBody());
        Assertions.assertTrue(saleResponseEntity.getBody().isEmpty());
        Assertions.assertEquals(HttpStatus.OK, saleResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getSalesById returns saleresponse when successful")
    void getSalesById_returnsSaleresponse_whenSuccessful(){
        sellerRepository.save(SellerCreator.createSeller());
        Sale expectedSale = saleRepository.save(SaleCreator.createSale());

        SaleResponse saleResponse = testRestTemplate.getForObject("/api/sales/by-id/{id}", SaleResponse.class, expectedSale.getId());

        Assertions.assertNotNull(saleResponse);
        Assertions.assertEquals(expectedSale.getValue(), saleResponse.getValue());
        Assertions.assertEquals(expectedSale.getId(), saleResponse.getId());
    }

    @Test
    @DisplayName("updateSale updates sale when successful")
    void updateSale_updatesSale_whenSuccessful(){
        sellerRepository.save(SellerCreator.createSeller());
        saleRepository.save(SaleCreator.createSale());
            
        ResponseEntity<Void> entity = testRestTemplate.exchange("/api/sales", HttpMethod.PUT,
                new HttpEntity<>(SaleRequestCreator.createSaleRequestToUpdateSale()), Void.class);


        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("deleteSale removes sale when successful")
    void deleteSale_removesSale_whenSuccessful(){
        sellerRepository.save(SellerCreator.createSeller());
        Sale sale = saleRepository.save(SaleCreator.createSale());
            
        ResponseEntity<Void> entity = testRestTemplate.exchange("/api/sales/by-id/{id}  ", HttpMethod.DELETE,
                null, Void.class, sale.getId());


        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }
}   
