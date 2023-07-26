package com.br.compassuol.sp.challenge.msorders.repository;

import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO - juntar os dois
public interface AddressRepository extends JpaRepository<DeliveryAddress, Long> {
    Boolean existsByZipCodeAndNumber(String zipCode, String number);
    DeliveryAddress findByZipCodeAndNumber(String zipCode, String number);
}
