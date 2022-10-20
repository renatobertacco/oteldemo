package org.demo.rental.app.restclient;

import java.util.List;

import org.demo.rental.app.dto.BikeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bike", url = "${bike.url}")
public interface BikeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/bike/bikes/listBooked")
    List<BikeDTO> listBooked();

    @RequestMapping(method = RequestMethod.GET, value = "/bike/bikes/listAvailables")
    List<BikeDTO> listAvailables(@RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "size", required = false) String size);

    @RequestMapping(method = RequestMethod.PUT, value = "/bike/bikes/{bikeId}/rentBy/{clientId}")
    BikeDTO rent(@PathVariable(name = "bikeId") Long bikeId,
            @PathVariable(name = "clientId", required = false) Long clientId);

}
