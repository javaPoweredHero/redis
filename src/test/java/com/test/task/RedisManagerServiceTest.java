package com.test.task;

import com.test.task.config.TaskApplicationConfig;
import com.test.task.dal.domain.Car;
import com.test.task.services.api.RedisManagerService;
import com.test.task.services.impl.RedisContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisManagerServiceTest {

    @Autowired RedisManagerService managerService;
    @Autowired RedisContainer<Car> carContainer;
    @Autowired TaskApplicationConfig applicationConfig;
    private Map<Object, Car> carMap = TestDataProvider.buildCarMap(10);

    @After
    public void cleanUp() {
        managerService.resetDefaults();
        carContainer.clear();
    }

    @Before
    public void initialize() {
        carContainer.putAll(carMap);
    }

    @Test
    public void changeConfigWorks() {
        managerService.setClusterNodesConfig(Collections.singletonList(getUnreachableAddr()));
    }

    // #ToDo handler...
    @Test(expected = RedisConnectionFailureException.class)
    public void unreachableNodeFails() {
        managerService.setClusterNodesConfig(Collections.singletonList(getUnreachableAddr()));
        Assert.assertEquals(carMap, carContainer);
    }

    @Test(expected = RedisConnectionFailureException.class)
    public void resetDefaultsWorks() {
        managerService.setClusterNodesConfig(Collections.singletonList(getUnreachableAddr()));
        try {
            carContainer.entrySet();
        } catch (RedisConnectionFailureException ex) {
            managerService.resetDefaults();
            Assert.assertEquals(carMap, carContainer);
            throw ex;
        }
        Assert.fail();
    }

    @Test(expected = RedisConnectionFailureException.class)
    public void removeNodeWorks() {
        applicationConfig.getNodes().forEach(s -> managerService.removeNode(s));
        carContainer.entrySet();
    }

    @Test()
    public void addNodeWorks() {
        applicationConfig.getNodes().forEach(s -> managerService.removeNode(s));
        managerService.addNode(applicationConfig.getNodes().iterator().next());
        carContainer.entrySet();
    }

    private static String getUnreachableAddr() {
        return "1270.0.0:3002";
    }
}
