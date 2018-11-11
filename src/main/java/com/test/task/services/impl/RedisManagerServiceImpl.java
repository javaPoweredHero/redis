package com.test.task.services.impl;

import com.test.task.config.TaskApplicationConfig;
import com.test.task.services.api.RedisManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RedisManagerServiceImpl implements RedisManagerService {

    @Autowired
    @Qualifier("jedis-connection-factory")
    private JedisConnectionFactory connectionFactory;

    @Autowired
    private TaskApplicationConfig applicationConfig;

    @Override
    public void setClusterNodesConfig(Collection<RedisNode> nodesConfig) {
        connectionFactory.destroy();
        connectionFactory.getClusterConfiguration().setClusterNodes(nodesConfig);
        connectionFactory.afterPropertiesSet();
    }

    @Override
    public void setClusterNodesConfig(List<String> nodes) {
        setClusterNodesConfig(simpleStringToRedisNodes(nodes));
    }

    @Override
    public void resetDefaults() {
        setClusterNodesConfig(simpleStringToRedisNodes(applicationConfig.getNodes()));
    }

    @Override
    public void addNode(RedisNode node) {
        Set<RedisNode> proxyNodes = new HashSet<>(connectionFactory.getClusterConfiguration().getClusterNodes());
        proxyNodes.add(node);
        setClusterNodesConfig(proxyNodes);
    }

    @Override
    public void addNode(String node) {
        addNode(simpleStringToRedisNode(node));
    }

    @Override
    public void removeNode(RedisNode node) {
        Set<RedisNode> proxyNodes = new HashSet<>(connectionFactory.getClusterConfiguration().getClusterNodes());
        proxyNodes.remove(node);
        setClusterNodesConfig(proxyNodes);

    }

    @Override
    public void removeNode(String node) {
        removeNode(simpleStringToRedisNode(node));
    }

    private static Collection<RedisNode> simpleStringToRedisNodes(List<String> nodes) {
        return nodes.stream()
                .map(RedisManagerServiceImpl::simpleStringToRedisNode)
                .collect(Collectors.toList());
    }

    private static RedisNode simpleStringToRedisNode(String node) {
        int index = node.indexOf(":");
        String host = node.substring(0, index);
        String port = node.substring(index + 1, node.length());
        return new RedisNode(host, Integer.valueOf(port));
    }
}
