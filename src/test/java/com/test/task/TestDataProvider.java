package com.test.task;

import com.test.task.dal.domain.Car;
import com.test.task.dal.domain.Truck;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@UtilityClass
public class TestDataProvider {

    private int carCount;
    private int truckCount;

    public void resetCounters() {
        carCount = truckCount = 0;
    }

    public Map<Object, Car> buildCarMap(int size) {
        return Stream.generate(() -> {
            Car car = buildCar();
            return new AbstractMap.SimpleEntry<Object, Car>(car.getKey(), car);
        })
                .limit(size)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public Map<Object, Truck> buildTruckMap(int size) {
        return Stream.generate(() -> {
            Truck truck = buildTruck();
            return new AbstractMap.SimpleEntry<Object, Truck>(truck.getKey(), truck);
        })
                .limit(size)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public Car buildCar() {
        return new Car()
                .setKey(String.valueOf(++carCount))
                .setName("car " + carCount)
                .setModel("mazda " + carCount)
                .setYear(carCount);
    }

    public Truck buildTruck() {
        return new Truck()
                .setKey(String.valueOf(++truckCount))
                .setName("truck " + truckCount)
                .setWeight(carCount + truckCount);
    }

}
