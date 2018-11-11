package com.test.task.dal.repository.betterWay;

import com.test.task.dal.domain.Truck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckRedisRepository extends CrudRepository<Truck, String> {

    List<Truck> findByName(String name);
}
