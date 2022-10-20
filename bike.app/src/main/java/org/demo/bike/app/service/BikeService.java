package org.demo.bike.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.demo.bike.app.dto.BikeDTO;
import org.demo.bike.app.dto.SizeDTO;
import org.demo.bike.app.model.Bike;
import org.demo.bike.app.model.Size;
import org.demo.bike.app.repo.BikeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class BikeService {

    @Autowired
    private BikeRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    public List<BikeDTO> findAll() {
        return apply((List<BikeDTO>) null, i -> repo.findAll());
    }

    public List<BikeDTO> findAll(Long id, String model, SizeDTO size, Long clientId) {
        Bike bike = new Bike();
        bike.setId(id);
        bike.setModel(model);
        if (size != null) {
            bike.setSize(Size.valueOf(size.name()));
        }
        bike.setRentedBy(clientId);
        return apply((List<BikeDTO>) null, i -> repo.findAll(Example.of(bike, ExampleMatcher.matchingAll()
                .withMatcher("model", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()))));
    }

    public List<BikeDTO> findAllBooked() {
        return apply((List<BikeDTO>) null, i -> repo.findByRentedByNotNull());
    }

    public List<BikeDTO> findAllAvailables(String model, SizeDTO size) {
        Bike bike = new Bike();
        bike.setModel(model);
        if (size != null) {
            bike.setSize(Size.valueOf(size.name()));
        }
        return apply((List<BikeDTO>) null, i -> repo.findAll(Example.of(bike, ExampleMatcher.matchingAll()
                .withMatcher("model", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()))));
    }

    public BikeDTO setRentedBy(long bikeId, Long clientId) {
        Bike bike = repo.findById(bikeId).orElse(null);
        if (bike != null) {
            bike.setRentedBy(clientId);
            return modelMapper.map(repo.save(bike), BikeDTO.class);
        } else {
            return null;
        }
    }

    public BikeDTO findById(Long id) {
        Optional<Bike> bike = repo.findById(id);
        return bike.map(b -> modelMapper.map(b, BikeDTO.class)).orElse(null);
    }

    public BikeDTO save(BikeDTO bike) {
        return apply(bike, repo::save);
    }

    public List<BikeDTO> saveAll(final Collection<BikeDTO> bikes) {
        return apply(bikes, repo::saveAll);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void createDemoData() {
        List<Bike> bikes = new ArrayList<>();
        List<String> models = Arrays.asList("Canyon Spectral", "Canyon Strive​", "Propain Hugene", "Propain Tyee",
                "Rose Uncle Jimbo", "Ghost Lector", "Cube Stereo", "Transition Patrol", "Norco Sight",
                "Santacruz Nomad", "Santacruz Hightower");
        for (String model : models) {
            for (Size size : Size.values()) {
                Bike bike = new Bike();
                bike.setModel(model);
                switch (size) {
                case XXS:
                case XS:
                    bike.setRate(20);
                    break;
                case S:
                case M:
                    bike.setRate(40);
                    break;
                case L:
                    bike.setRate(50);
                    break;
                default:
                    bike.setRate(60);
                    break;
                }
                bike.setSize(size);
                bikes.add(bike);
            }
        }
        repo.saveAll(bikes);
    }

    private BikeDTO apply(BikeDTO input, Function<Bike, Bike> f) {
        Bike entity = f.apply(input != null ? modelMapper.map(input, Bike.class) : null);
        return modelMapper.map(entity, BikeDTO.class);
    }

    private List<BikeDTO> apply(Collection<BikeDTO> input, Function<List<Bike>, List<Bike>> f) {
        List<Bike> entities = f.apply(input != null ? modelMapper.map(input, new TypeToken<List<Bike>>() {
        }.getType()) : null);
        return modelMapper.map(entities, new TypeToken<List<BikeDTO>>() {
        }.getType());
    }

}
