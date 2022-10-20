package org.demo.rental.app.restclient;

import java.util.Collection;
import java.util.List;

import org.demo.rental.app.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client", url = "${client.url}")
public interface ClientClient {

    @RequestMapping(method = RequestMethod.GET, value = "client/clients/{id}")
    ClientDTO findById(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.POST, value = "client/clients/{id}")
    List<ClientDTO> findByIds(@PathVariable("id") Collection<Long> ids);

    @RequestMapping(method = RequestMethod.GET, value = "client/clients")
    List<ClientDTO> findAll(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "email", required = false) String email, @RequestParam(name = "idCard", required = false) String idCard,
            @RequestParam(name = "phone", required = false) String phone);
}
