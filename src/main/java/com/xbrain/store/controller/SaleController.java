package com.xbrain.store.controller;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.model.Sale;
import com.xbrain.store.service.SaleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest saleRequest){
        return ResponseEntity.status(HttpStatus.OK).body(saleService.createSale(saleRequest));
    }
}
