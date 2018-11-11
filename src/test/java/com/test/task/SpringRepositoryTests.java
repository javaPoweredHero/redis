package com.test.task;

import com.test.task.dal.domain.Truck;
import com.test.task.dal.repository.betterWay.TruckRedisRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRepositoryTests {

    @Autowired TruckRedisRepository redisRepository;

    @Test
    public void springRedisTest() {
        Truck testTruck = TestDataProvider.buildTruck();
        Truck anotherTruck = TestDataProvider.buildTruck();
        anotherTruck.setName(testTruck.getName());
        redisRepository.save(testTruck);
        Assert.assertEquals(testTruck, redisRepository.findAll().iterator().next());
        Assert.assertEquals(testTruck, redisRepository.findByName(testTruck.getName()).iterator().next());

        redisRepository.save(anotherTruck);
        Assert.assertEquals(Arrays.asList(testTruck, anotherTruck), redisRepository.findByName(testTruck.getName()));
        redisRepository.deleteAll();
    }

}
