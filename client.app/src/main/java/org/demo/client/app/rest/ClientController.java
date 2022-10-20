package org.demo.client.app.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.demo.client.app.dto.ClientDTO;
import org.demo.client.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
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
    public List<ClientDTO> findAll(@RequestParam MultiValueMap<String, String> multiValueParams) {
        if (multiValueParams.isEmpty()) {
            return service.findAll();
        } else if (multiValueParams.get("id") != null && multiValueParams.get("id").size() > 1) {
            return service.findByIds(multiValueParams.get("id").stream().map(this::toLong).collect(Collectors.toSet()));
        } else {
            Map<String, String> params = multiValueParams.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().get(0)));
            return service.findAll(toLong(params.get("id")), params.get("name"), params.get("email"),
                    params.get("idCard"), params.get("phone"));
        }
    }

    private Long toLong(String v) {
        return v != null ? Long.valueOf(v) : null;
    }

    @GetMapping("{id}")
    public ClientDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
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
