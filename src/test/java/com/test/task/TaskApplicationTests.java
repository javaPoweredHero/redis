package com.test.task;

import com.test.task.dal.domain.Car;
import com.test.task.dal.domain.Truck;
import com.test.task.services.impl.RedisContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskApplicationTests {

    @Autowired RedisContainer<Car> carContainer;
    @Autowired RedisContainer<Truck> truckContainer;

    private int carCount;
    private int truckCount;

    @After
    public void initialize() {
        carContainer.clear();
        truckContainer.clear();
        carCount = truckCount = 0;
    }

    @Test
    public void contextLoads() {
        Assert.assertNotNull(carContainer);
    }

    @Test
    public void additionWorks() {
        Car testCar = buildCar();
        Truck testTruck = buildTruck();
        putSingleData(testCar, testTruck);
        Assert.assertEquals(carContainer.entrySet().iterator().next().getValue(), testCar);
        Assert.assertEquals(truckContainer.entrySet().iterator().next().getValue(), testTruck);
    }

    @Test
    public void multiAdditionWorks() {
        Map<Object, Car> map = buildCarMap(10);
        carContainer.putAll(map);
        Assert.assertEquals(carContainer.entrySet().size(), 10);
    }

    @Test
    public void getOneWorks() {
        Car testCar = buildCar();
        Truck testTruck = buildTruck();
        putSingleData(testCar, testTruck);
        Assert.assertEquals(carContainer.get(testCar.getKey()), testCar);
        Assert.assertEquals(truckContainer.get(testTruck.getKey()), testTruck);
    }


    private void putSingleData(Car testCar, Truck testTruck) {
        carContainer.put(testCar.getKey(), testCar);
        truckContainer.put(testTruck.getKey(), testTruck);
    }

    private Map<Object, Car> buildCarMap(int size) {
        return Stream.generate(() -> {
            Car car = buildCar();
            return new AbstractMap.SimpleEntry<Object, Car>(car.getKey(), car);
        })
                .limit(size)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    private Car buildCar() {
        return new Car()
                .setKey(String.valueOf(++carCount))
                .setKey("car " + carCount)
                .setModel("mazda " + carCount)
                .setYear(carCount);
    }

    private Truck buildTruck() {
        return new Truck()
                .setKey(String.valueOf(++truckCount))
                .setName("truck " + truckCount)
                .setWeight(carCount + truckCount);
    }
}
