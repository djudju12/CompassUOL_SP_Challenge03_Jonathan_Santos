package com.br.compassuol.sp.challenge.msorders.repository;

import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /* With @Query when the user of the API uses an invalid Sort parameter
     * the parameter is passed right away to the query, this causes the
     * application not throw the PropertyReferenceException, returning 500
     * withouth a proper message.
     */
    Page<Order> findAllByStatusIsNot(OrderStatus orderStatus, Pageable pageable);
    @Query("SELECT o FROM Order o WHERE o.id = ?1 AND o.status <> 'CANCELED'")
    Optional<Order> findByIdActive(long id);
}
