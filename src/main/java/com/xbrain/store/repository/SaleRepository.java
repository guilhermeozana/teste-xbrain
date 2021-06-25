package com.xbrain.store.repository;

import java.util.List;
import java.util.Optional;

import com.xbrain.store.model.Sale;
import com.xbrain.store.model.Seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

    List<Sale> findAllBySellerId(Long id);
}
