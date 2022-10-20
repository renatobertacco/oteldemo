package org.demo.rental.app.rest;

import java.util.List;

import org.demo.rental.app.dto.BikeDTO;
import org.demo.rental.app.dto.BikeRentalDTO;
import org.demo.rental.app.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("listRented")
    public List<BikeRentalDTO> listBikeRented() {
        return rentalService.listRented();
    }

    @GetMapping("listRented2")
    public List<BikeRentalDTO> listBikeRented2() {
        return rentalService.listRented2();
    }

    @GetMapping("listAvailables")
    public List<BikeDTO> listAvailables(@RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "size", required = false) String size) {
        return rentalService.findAllAvailables(model, size);
    }

    @PutMapping("startRental")
    public BikeRentalDTO startRental(@RequestParam(name = "bikeId") Long bikeId,
            @RequestParam(name = "clientId") Long clientId) {
        return rentalService.startRental(bikeId, clientId);
    }

    @PutMapping("stopRental")
    public BikeDTO stopRental(@RequestParam(name = "bikeId") Long bikeId) {
        return rentalService.stopRental(bikeId);
    }
}
