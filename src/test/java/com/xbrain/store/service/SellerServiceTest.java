package com.xbrain.store.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.exception.SellerNotFoundException;
import com.xbrain.store.model.Seller;
import com.xbrain.store.repository.SaleRepository;
import com.xbrain.store.repository.SellerRepository;
import com.xbrain.store.util.SaleCreator;
import com.xbrain.store.util.SellerCreator;
import com.xbrain.store.util.SellerRequestCreator;
import com.xbrain.store.util.SellerResponseCreator;
import com.xbrain.store.util.StringDateCreator;

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
public class SellerServiceTest {
    @Mock
    private SellerService sellerService;
    @Mock
    private SellerRepository sellerRepositoryMock;
    @Mock
    private SaleRepository saleRepositoryMock;
    

    @BeforeEach
    void setUp(){
        BDDMockito.when(sellerRepositoryMock.save(ArgumentMatchers.any(Seller.class)))
                .thenReturn(SellerCreator.createSeller());

        BDDMockito.when(sellerRepositoryMock.findAll())
                .thenReturn(List.of(SellerCreator.createSeller()));

        BDDMockito.when(saleRepositoryMock.findBySellerIdAndDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(List.of(SaleCreator.createSale()));

        BDDMockito.when(sellerRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(SellerCreator.createSeller()));
        
        BDDMockito.doNothing().when(sellerService).updateSeller(ArgumentMatchers.any(SellerRequest.class));

        BDDMockito.doNothing().when(sellerService).deleteSellerById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("createSeller saves seller and returns sellerresponse when successful")
    void createSeller_savesSellerAndReturnsSellerResponse_whenSuccessful(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        SellerResponse sellerResponse = sellerService.createSeller(SellerRequestCreator.createSellerRequestToSaveSeller());
        
        Assertions.assertNotNull(sellerResponse);
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse() , sellerResponse);
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse().getName() , sellerResponse.getName());
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse().getId() , sellerResponse.getId());
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse().getTotalSales() , sellerResponse.getTotalSales());
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse().getAverageDailySales(), sellerResponse.getAverageDailySales());
    }

    @Test
    @DisplayName("getAllSellers returns list of sellerresponse when successful")
    void getAllSellers_returnsListOfSellerResponse_whenSuccessful(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        List<SellerResponse> sellers = sellerService.getAllSellers();
        
        Assertions.assertNotNull(sellers);
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse() , sellers.get(0));
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse().getName() , sellers.get(0).getName());
        Assertions.assertEquals(SellerResponseCreator.createSellerResponse().getId() , sellers.get(0).getId());
    }

    @Test
    @DisplayName("getAllSellers returns empty list when successful")
    void getAllSellers_returnsEmptyList_whenNoSellerIsFound(){
        BDDMockito.when(sellerRepositoryMock.findAll())
                .thenReturn(List.of());

        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        List<SellerResponse> sellers = sellerService.getAllSellers();
        
        Assertions.assertNotNull(sellers);
        Assertions.assertTrue(sellers.isEmpty());
    }

    @Test
    @DisplayName("getAllSellersBySalesPerPeriod returns list of sellerresponse by sales per period when successful")
    void getAllSellersBySalesPerPeriod_returnsListOfSellerResponseBySalesPerPeriod_whenSuccessful(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        String expectedName = SellerResponseCreator.createSellerResponse().getName();
        Long expectedId = SellerResponseCreator.createSellerResponse().getId();

        List<SellerResponse> sellersResponseBySalesPeriod = sellerService.getSellersBySalesPerPeriod(StringDateCreator.createStringBrDate() ,StringDateCreator.createStringBrDate());

        Assertions.assertNotNull(sellersResponseBySalesPeriod);
        Assertions.assertFalse(sellersResponseBySalesPeriod.isEmpty());
        Assertions.assertTrue(sellersResponseBySalesPeriod.size() == 1);
        Assertions.assertEquals(expectedName, sellersResponseBySalesPeriod.get(0).getName());
        Assertions.assertEquals(expectedId, sellersResponseBySalesPeriod.get(0).getId());
    }

    @Test
    @DisplayName("getAllSellersBySalesPerPeriod returns list of sellerresponse by sales per period when successful")
    void getAllSellersBySalesPerPeriod_returnsEmptyList_whenNoSellerIsFound(){
        
        BDDMockito.when(saleRepositoryMock.findBySellerIdAndDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(List.of());

        List<SellerResponse> sellersBySalesPerPeriod = sellerService.getSellersBySalesPerPeriod("22/06/2021", "22/06/2021");

        Assertions.assertNotNull(sellersBySalesPerPeriod);
        Assertions.assertTrue(sellersBySalesPerPeriod.isEmpty());
    }

    @Test
    @DisplayName("getSellerById returns sellerresponse when successful")
    void getSellerById_returnsSellerResponse_whenSuccessful(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        String expectedName = SellerResponseCreator.createSellerResponse().getName();
        Long expectedId = SellerResponseCreator.createSellerResponse().getId();

        SellerResponse sellerResponse = sellerService.getSellerById(1L);

        Assertions.assertNotNull(sellerResponse);
        Assertions.assertEquals(expectedName, sellerResponse.getName());
        Assertions.assertEquals(expectedId, sellerResponse.getId());
    }

    @Test
    @DisplayName("getSellerById throws SellerNotFoundException when no seller is found")
    void getSellerById_throwsSellerNotFoundException_whenNoSellerIsFound(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        BDDMockito.when(sellerRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new SellerNotFoundException("No seller found"));

        Assertions.assertThrows(SellerNotFoundException.class, () -> sellerService.getSellerById(1L));
    }

    @Test
    @DisplayName("updateSale updates sale when successful")
    void updateSale_updatesSale_whenSuccessful(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        Assertions.assertDoesNotThrow(() -> sellerService.updateSeller(SellerRequestCreator.createSellerRequestToUpdateSeller()));
    }

    @Test
    @DisplayName("deleteSellerById removes seller when successful")
    void deleteSellerById_removesSeller_whenSuccessful(){
        SellerService sellerService = new SellerService(sellerRepositoryMock, saleRepositoryMock);

        Assertions.assertDoesNotThrow(() -> sellerService.deleteSellerById(1L));
    }    
}
