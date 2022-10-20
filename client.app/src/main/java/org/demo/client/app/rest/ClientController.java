package org.demo.client.app.rest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.demo.client.app.dto.ClientDTO;
import org.demo.client.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("client/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping()
    public List<ClientDTO> findAll(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            return service.findAll();
        } else {
            return service.findAll(toLong(params.get("id")), params.get("name"), params.get("email"),params.get("idCard"),params.get("phone"));
        }
    }
    private Long toLong(String v) {
        return v != null ? Long.valueOf(v) : null;
    }

    @GetMapping("{id}")
    public ClientDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping("{id}")
    public List<ClientDTO> findById(@PathVariable("id") Set<Long> ids) {
        return service.findByIds(ids);
    }
    @PostMapping()
    public ClientDTO create(@RequestBody ClientDTO client) {
        return service.create(client);
    }

    @PutMapping("{id}")
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO client) {
        client.setId(id);
        return service.update(client);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
