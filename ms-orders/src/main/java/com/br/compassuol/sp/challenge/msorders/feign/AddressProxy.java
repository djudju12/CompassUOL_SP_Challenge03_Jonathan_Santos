package com.br.compassuol.sp.challenge.msorders.feign;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "https://viacep.com.br/ws/")
public interface AddressProxy {

    //TODO - e se falhar? e se demorar?
    @GetMapping("{cep}/json")
    AddressResponse getAddressByCep(@PathVariable(required = true) String cep);
}
