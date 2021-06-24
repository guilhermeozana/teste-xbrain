package com.xbrain.store.model;

import com.xbrain.store.model.Seller;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private Double value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId", referencedColumnName = "id")
    @JoinColumn(name = "sellerName", referencedColumnName = "name")
    private Seller seller;
}
