package com.test.task.services.api;

import org.springframework.data.redis.connection.RedisNode;

import java.util.Collection;
import java.util.List;

public interface RedisManagerService {

    void setClusterNodesConfig(Collection<RedisNode> nodesConfig);

    void setClusterNodesConfig(List<String> nodes);

    void resetDefaults();

    void addNode(RedisNode node);

    void addNode(String node);

    void removeNode(RedisNode node);

    void removeNode(String node);

}
