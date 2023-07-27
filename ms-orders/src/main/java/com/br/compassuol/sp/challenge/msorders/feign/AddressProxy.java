package com.br.compassuol.sp.challenge.msorders.feign;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.AddressResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "https://viacep.com.br/ws/")
@Validated
public interface AddressProxy {
    @GetMapping("{cep}/json")
    AddressResponse getAddressByCep(@PathVariable
                                    @Valid
                                        @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$",
                                                 message = "CEP must have 5 digits, a dash, and 3 digits")
                                    String cep);
}
