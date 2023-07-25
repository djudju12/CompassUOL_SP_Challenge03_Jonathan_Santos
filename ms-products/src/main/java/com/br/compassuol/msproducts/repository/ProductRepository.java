package com.br.compassuol.msproducts.repository;

import com.br.compassuol.msproducts.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
