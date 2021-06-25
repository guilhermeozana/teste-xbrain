package com.xbrain.store.controller;

import java.util.List;

import com.xbrain.store.dto.SellerRequest;
import com.xbrain.store.dto.SellerResponse;
import com.xbrain.store.model.Seller;
import com.xbrain.store.service.SellerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/getall")
    public ResponseEntity<List<SellerResponse>> getSellers(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getSellers());
    }
}
