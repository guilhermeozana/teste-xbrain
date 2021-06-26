package com.xbrain.store.repository;

import java.time.LocalDate;
import java.util.List;

import com.xbrain.store.model.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

    List<Sale> findAllBySellerId(Long id);

    // @Query(name = "select * from Sale where sellerId = :id between :from and :to")
	List<Sale> findBySellerIdAndDateBetween(Long id, LocalDate from, LocalDate to);
}
