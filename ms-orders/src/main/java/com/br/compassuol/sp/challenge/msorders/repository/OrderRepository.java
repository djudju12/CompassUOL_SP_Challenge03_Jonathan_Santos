package com.br.compassuol.sp.challenge.msorders.repository;

import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Writing queries looks cool
    @Query("SELECT o FROM Order o WHERE o.status <> 'CANCELED'")
    Page<Order> findAllActiveOrders(Pageable pageable);
    @Query("SELECT o FROM Order o WHERE o.id = ?1 AND o.status <> 'CANCELED'")
    Optional<Order> findByIdActive(long id);
}
