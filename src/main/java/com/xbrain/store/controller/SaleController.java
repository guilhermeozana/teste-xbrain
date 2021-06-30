package com.xbrain.store.controller;

import java.util.List;

import com.xbrain.store.dto.SaleRequest;
import com.xbrain.store.dto.SaleResponse;
import com.xbrain.store.service.SaleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(saleRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SaleResponse>> getAllSales(){
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSales());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getSaleById(id));
    }

    @PutMapping
    public ResponseEntity<Void> updateSale(@RequestBody SaleRequest saleRequest){
        saleService.updateSale(saleRequest);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<Void> deleteSaleById(@PathVariable Long id){
        saleService.deleteSaleById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}