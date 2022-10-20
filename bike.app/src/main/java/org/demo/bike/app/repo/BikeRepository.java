package org.demo.bike.app.repo;

import java.util.List;

import org.demo.bike.app.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

    List<Bike> findByRentedByNotNull();

}
