package org.demo.bike.app.rest;

import static org.demo.bike.app.util.Converter.toLong;
import static org.demo.bike.app.util.Converter.toSizeDTO;

import java.util.List;
import java.util.Map;

import org.demo.bike.app.dto.BikeDTO;
import org.demo.bike.app.service.BikeService;
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
@RequestMapping("bike/bikes")
public class BikeController {

    @Autowired
    private BikeService service;

    @GetMapping()
    public List<BikeDTO> findAll(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            return service.findAll();
        } else {
            return service.findAll(toLong(params.get("id")), params.get("model"), toSizeDTO(params.get("size")), toLong(params.get("clientId")));
        }
    }

    @GetMapping("listBooked")
    public List<BikeDTO> listBooked() {
        return service.findAllBooked();
    }

    @GetMapping("listAvailables")
    public List<BikeDTO> listAvailables(@RequestParam Map<String, String> params) {
            return service.findAllAvailables(params.get("model"), toSizeDTO(params.get("size")));
    }

    @GetMapping("/{id}")
    public BikeDTO find(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public BikeDTO create(@RequestBody BikeDTO bike) {
        return service.save(bike);
    }

    @PutMapping("/{id}")
    public BikeDTO update(@PathVariable Long id, @RequestBody BikeDTO bike) {
        bike.setId(id);
        return service.save(bike);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{bikeId}/rentBy")
    public BikeDTO removeRentedBy(@PathVariable(name = "bikeId") Long bikeId) {
        return service.setRentedBy(bikeId, null);
    }
    
    @PutMapping("/{bikeId}/rentBy/{clientId}")
    public BikeDTO setRentedBy(@PathVariable(name = "bikeId") Long bikeId, @PathVariable(name = "clientId", required = false) Long clientId) {
        return service.setRentedBy(bikeId, clientId);
    }

}
