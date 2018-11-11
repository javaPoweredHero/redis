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

import java.util.Map;

import static com.test.task.TestDataProvider.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisContainerTests {

    @Autowired RedisContainer<Car> carContainer;
    @Autowired RedisContainer<Truck> truckContainer;

    @After
    public void cleanUp() {
        carContainer.clear();
        truckContainer.clear();
        TestDataProvider.resetCounters();
    }

    @Test
    public void additionWorks() {
        Car testCar = TestDataProvider.buildCar();
        Truck testTruck = TestDataProvider.buildTruck();
        putSingleData(testCar, testTruck);
        Assert.assertEquals(testCar, carContainer.entrySet().iterator().next().getValue());
        Assert.assertEquals(testTruck, truckContainer.entrySet().iterator().next().getValue());
    }

    @Test
    public void multiAdditionWorks() {
        Map<Object, Car> map = TestDataProvider.buildCarMap(10);
        carContainer.putAll(map);
        Assert.assertEquals(map, carContainer);
    }

    @Test
    public void getOneWorks() {
        Car testCar = TestDataProvider.buildCar();
        Truck testTruck = TestDataProvider.buildTruck();
        putSingleData(testCar, testTruck);
        Assert.assertEquals(testCar, carContainer.get(testCar.getKey()));
        Assert.assertEquals(testTruck, truckContainer.get(testTruck.getKey()));
    }

    @Test
    public void getOneWithWrongKeyNotWorks() {
        Car testCar = TestDataProvider.buildCar();
        Truck testTruck = TestDataProvider.buildTruck();
        putSingleData(testCar, testTruck);
        Assert.assertNull(carContainer.get(testTruck));
        Assert.assertNull(truckContainer.get(testCar));
    }

    @Test
    public void sizeWorks() {
        final int size = 150;
        Map<Object, Car> map = TestDataProvider.buildCarMap(size);
        carContainer.putAll(map);
        Assert.assertEquals(size, carContainer.size());
    }

    @Test
    public void removeWorks() {
        final int size = 100;
        Map<Object, Car> map = putMultiCarData(size);
        Car car = map.get(String.valueOf(size / 2));
        Assert.assertNotNull(carContainer.get(car.getKey()));
        carContainer.remove(car.getKey());
        Assert.assertNull(carContainer.get(car.getKey()));
        Assert.assertEquals(size - 1, carContainer.size());
    }

    @Test
    public void removeWrongKeyNotWorks() {
        final int size = 50;
        Map<Object, Car> map = putMultiCarData(size);
        Car car = map.get(String.valueOf(size / 2));
        Assert.assertNotNull(carContainer.get(car.getKey()));
        carContainer.remove(car);
        Assert.assertNotNull(carContainer.get(car.getKey()));
        Assert.assertEquals(size, carContainer.size());
    }

    @Test
    public void clearWorks() {
        final int size = 50;
        carContainer.putAll(TestDataProvider.buildCarMap(size));
        carContainer.clear();
        Assert.assertEquals(0, carContainer.size());
    }

    @Test
    public void containsKeyWorks() {
        final int size = 1000;
        Map<Object, Car> map = putMultiCarData(size);
        Car car = map.get(String.valueOf(size / 2));
        Assert.assertTrue(carContainer.containsKey(car.getKey()));
        Assert.assertFalse(carContainer.containsKey(buildTruck()));
        Assert.assertFalse(carContainer.containsKey(car.getYear()));
    }

    @Test
    public void containsValueWorks() {
        final int size = 600;
        Map<Object, Car> map = putMultiCarData(size);
        Car car = map.get(String.valueOf(size / 2));
        Assert.assertTrue(carContainer.containsValue(car));
        Assert.assertFalse(carContainer.containsValue(buildTruck()));
        Assert.assertFalse(carContainer.containsValue(car.getKey()));
        Assert.assertFalse(carContainer.containsValue(buildCar()));
    }

    @Test
    public void keySetWorks() {
        final int size = 300;
        Map<Object, Car> map = buildCarMap(size);
        carContainer.putAll(map);
        Assert.assertEquals(map.keySet(), carContainer.keySet());
    }

    @Test
    public void valuesWorks() {
        final int size = 40;
        Map<Object, Car> map = buildCarMap(size);
        carContainer.putAll(map);
        Assert.assertTrue(map.values().containsAll(carContainer.values()));
        Assert.assertEquals(map.values().size(), carContainer.values().size());
    }

    private Map<Object, Car> putMultiCarData(int size) {
        Map<Object, Car> map = buildCarMap(size);
        carContainer.putAll(map);
        return map;
    }

    private void putSingleData(Car testCar, Truck testTruck) {
        carContainer.put(testCar.getKey(), testCar);
        truckContainer.put(testTruck.getKey(), testTruck);
    }
}
