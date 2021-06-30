package com.xbrain.store.integration;

import java.time.LocalDate;
import java.util.List;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;
import com.xbrain.store.repository.SaleRepository;
import com.xbrain.store.repository.SellerRepository;
import com.xbrain.store.util.SaleCreator;
import com.xbrain.store.util.SellerCreator;
import com.xbrain.store.util.SellerRequestCreator;

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
public class SellerControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    @DisplayName("createSeller returns sellerresponse when successful")
    void createSale_savesSaleAndReturnsSaleresponse_whenSuccessful(){
        SellerRequest sellerRequest = SellerRequestCreator.createSellerRequestToSaveSeller();

        ResponseEntity<SellerResponse> saleResponseEntity = testRestTemplate.postForEntity("/api/sellers", sellerRequest, SellerResponse.class);

        Assertions.assertNotNull(saleResponseEntity);
        Assertions.assertNotNull(saleResponseEntity.getBody());
        Assertions.assertNotNull(saleResponseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED, saleResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSellers returns list of sellerresponse when successful")
    void getAllSellers_returnsListOfSellerresponse_whenSuccessful(){
        Seller expectedSeller = sellerRepository.save(SellerCreator.createSeller());

        ResponseEntity<List<SellerResponse>> sellersResponseEntity = testRestTemplate.exchange("/api/sellers/all", HttpMethod.GET, null, 
                new ParameterizedTypeReference<List<SellerResponse>>(){});

        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertFalse(sellersResponseEntity.getBody().isEmpty());
        Assertions.assertTrue(sellersResponseEntity.getBody().size() == 1);
        Assertions.assertEquals(expectedSeller.getName(), sellersResponseEntity.getBody().get(0).getName());
        Assertions.assertEquals(expectedSeller.getId(), sellersResponseEntity.getBody().get(0).getId());
    }

    @Test
    @DisplayName("getAllSellers returns empty list when no sale is found")
    void getAllSellers_returnsEmptyList_whenNoSaleIsFound(){

        ResponseEntity<List<SellerResponse>> saleResponseEntity = testRestTemplate.exchange("/api/sellers/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SellerResponse>>(){});

        Assertions.assertNotNull(saleResponseEntity.getBody());
        Assertions.assertTrue(saleResponseEntity.getBody().isEmpty());
        Assertions.assertEquals(HttpStatus.OK, saleResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getAllSellersBySalesPerPeriod returns all sellers by specific sales period when successful")
    void getAllSellersBySalesPerPeriod_returnsAllSellersBySpecificSalesPeriod_whenSuccessful(){

        Seller seller = SellerCreator.createSeller();
        Sale sale = SaleCreator.createSaleBySeller(seller);

        sale.setDate(LocalDate.of(2021, 06, 25));

        sellerRepository.save(seller);
        saleRepository.save(sale);

        ResponseEntity<List<SellerResponse>> sellersResponseEntity = testRestTemplate.exchange("/api/sellers/all-by-sales-period?from=25/06/2021&to=26/06/2021", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SellerResponse>>(){});

        Assertions.assertNotNull(sellersResponseEntity.getBody());
        Assertions.assertEquals(seller.getName(), sellersResponseEntity.getBody().get(0).getName());
        Assertions.assertEquals(seller.getId(), sellersResponseEntity.getBody().get(0).getId());
        Assertions.assertEquals(HttpStatus.OK, sellersResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("getSellerById returns sellerresponse when successful")
    void getSellerById_returnsSellerresponse_whenSuccessful(){
        Seller expectedSeller = sellerRepository.save(SellerCreator.createSeller());

        SellerResponse sellerResponse = testRestTemplate.getForObject("/api/sellers/by-id/{id}", SellerResponse.class, expectedSeller.getId());

        Assertions.assertNotNull(sellerResponse);
        Assertions.assertEquals(expectedSeller.getName(), sellerResponse.getName());
        Assertions.assertEquals(expectedSeller.getId(), sellerResponse.getId());
    }

    @Test
    @DisplayName("updateSeller updates seller when successful")
    void updateSeller_updatesSeller_whenSuccessful(){
        sellerRepository.save(SellerCreator.createSeller());
            
        ResponseEntity<Void> entity = testRestTemplate.exchange("/api/sellers", HttpMethod.PUT,
                new HttpEntity<>(SellerRequestCreator.createSellerRequestToUpdateSeller()), Void.class);


        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("deleteSeller removes seller when successful")
    void deleteSeller_removesSeller_whenSuccessful(){
        Seller seller = sellerRepository.save(SellerCreator.createSeller());
            
        ResponseEntity<Void> entity = testRestTemplate.exchange("/api/sellers/by-id/{id}  ", HttpMethod.DELETE,
                null, Void.class, seller.getId());

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }
    
}   
