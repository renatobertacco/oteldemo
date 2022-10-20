package org.demo.rental.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.demo.rental.app.dto.BikeDTO;
import org.demo.rental.app.dto.BikeRentalDTO;
import org.demo.rental.app.dto.ClientDTO;
import org.demo.rental.app.restclient.BikeClient;
import org.demo.rental.app.restclient.ClientClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private BikeClient bikeClient;

    @Autowired
    private ClientClient clientClient;

    public BikeRentalDTO startRental(long bikeId, long clientId) {
        BikeRentalDTO rental = new BikeRentalDTO();
        BikeDTO bike = bikeClient.rent(bikeId, clientId);
        ClientDTO client = clientClient.findById(clientId);
        modelMapper.map(bike, rental);
        modelMapper.map(client, rental);
        return rental;
    }

    public BikeDTO stopRental(long bikeId) {
        return bikeClient.rent(bikeId, null);
    }

    public List<BikeRentalDTO> listRented() {
        List<BikeRentalDTO> rentals = new ArrayList<>();
        List<BikeDTO> bikes = bikeClient.listBooked();
        for (BikeDTO bike : bikes) {
            ClientDTO client = clientClient.findById(bike.getRentedBy());
            BikeRentalDTO rental = new BikeRentalDTO();
            modelMapper.map(bike, rental);
            modelMapper.map(client, rental);
            rentals.add(rental);
        }
        return rentals;
    }

    public List<BikeRentalDTO> listRented2() {
        List<BikeRentalDTO> rentals = new ArrayList<>();
        List<BikeDTO> bikes = bikeClient.listBooked();
        if (!bikes.isEmpty()) {
            List<ClientDTO> clients = clientClient.findByIds(bikes.stream().map(BikeDTO::getRentedBy).collect(Collectors.toList()));
            Map<Long, ClientDTO> id2Client = clients.stream().collect(Collectors.toMap(ClientDTO::getId, Function.identity()));
            for (BikeDTO bike : bikes) {
                ClientDTO client = id2Client.get(bike.getRentedBy());
                BikeRentalDTO rental = new BikeRentalDTO();
                modelMapper.map(bike, rental);
                modelMapper.map(client, rental);
                rentals.add(rental);
            }
        }
        return rentals;
    }

    public List<BikeDTO> findAllAvailables(String model, String size) {
        return bikeClient.listAvailables(model, size);
    }
}
