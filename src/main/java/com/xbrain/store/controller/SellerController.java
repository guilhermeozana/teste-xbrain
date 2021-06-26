package com.xbrain.store.controller;

import java.util.List;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.service.SellerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<SellerResponse> createSeller(@RequestBody SellerRequest sellerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.createSeller(sellerRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SellerResponse>> getAllSellers(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getAllSellers());
    }

    @GetMapping("all-by-sales-period")
    public ResponseEntity<List<SellerResponse>> getAllSellersBySalesPerPeriod(
                @RequestParam("from") String fromString, @RequestParam("to") String toString){
       
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSellersBySalesPerPeriod(fromString, toString));
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<SellerResponse> getSellerById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSellerById(id));
    }

    @PutMapping
    public ResponseEntity<Void> updateSeller(@RequestBody SellerRequest sellerRequest){
        sellerService.updateSeller(sellerRequest);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("by-id/{id}")
    public ResponseEntity<Void> deleteSellerById(@PathVariable Long id){
        sellerService.deleteSellerById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
