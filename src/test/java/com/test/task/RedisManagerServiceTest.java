package com.test.task;

import com.test.task.config.TaskApplicationConfig;
import com.test.task.dal.domain.Truck;
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
    @Autowired RedisContainer<Truck> truckContainer;
    @Autowired TaskApplicationConfig applicationConfig;
    private Map<Object, Truck> truckMap = TestDataProvider.buildTruckMap(10);

    @After
    public void cleanUp() {
        managerService.resetDefaults();
        truckContainer.clear();
    }

    @Before
    public void initialize() {
        truckContainer.putAll(truckMap);
    }

    @Test
    public void changeConfigWorks() {
        managerService.setClusterNodesConfig(Collections.singletonList(getUnreachableAddr()));
    }

    // #ToDo handler...
    @Test(expected = RedisConnectionFailureException.class)
    public void unreachableNodeFails() {
        managerService.setClusterNodesConfig(Collections.singletonList(getUnreachableAddr()));
        Assert.assertEquals(truckMap, truckContainer);
    }

    @Test(expected = RedisConnectionFailureException.class)
    public void resetDefaultsWorks() {
        managerService.setClusterNodesConfig(Collections.singletonList(getUnreachableAddr()));
        try {
            truckContainer.entrySet();
        } catch (RedisConnectionFailureException ex) {
            managerService.resetDefaults();
            Assert.assertEquals(truckMap, truckContainer);
            throw ex;
        }
        Assert.fail();
    }

    @Test(expected = RedisConnectionFailureException.class)
    public void removeNodeWorks() {
        applicationConfig.getNodes().forEach(s -> managerService.removeNode(s));
        truckContainer.entrySet();
    }

    @Test()
    public void addNodeWorks() {
        applicationConfig.getNodes().forEach(s -> managerService.removeNode(s));
        managerService.addNode(applicationConfig.getNodes().iterator().next());
        truckContainer.entrySet();
    }

    private static String getUnreachableAddr() {
        return "1270.0.0:3002";
    }
}
