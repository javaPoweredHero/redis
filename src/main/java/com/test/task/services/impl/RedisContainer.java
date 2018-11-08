package com.test.task.services.impl;

import com.test.task.dal.domain.api.RedisEntity;
import com.test.task.dal.repository.api.RedisRepository;
import com.test.task.dal.repository.impl.RedisRepositoryImpl;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RedisContainer<T extends RedisEntity> implements Map<Object, T> {

    private final RedisRepository<T> redisRepository;

    public RedisContainer(Class<T> clazz, RedisTemplate<Object, Object> redisTemplate) {
        redisRepository = new RedisRepositoryImpl<>(clazz, redisTemplate);
    }

    @Override
    public int size() {
        return redisRepository.size().intValue();
    }

    @Override
    public boolean isEmpty() {
        return redisRepository.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return redisRepository.hasKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return redisRepository.findAll().containsValue(value);
    }

    @Override
    public T get(Object key) {
        return redisRepository.findOne(key);
    }

    @Override
    public T put(Object key, T value) {
        redisRepository.save(value);
        return value;
    }

    @Override
    public T remove(Object key) {
        T entity = redisRepository.findOne(key);
        if (entity != null) {
            redisRepository.delete(key);
        }
        return entity;
    }

    @Override
    public void putAll(Map<?, ? extends T> map) {
        redisRepository.saveAll(map);
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            redisRepository.removeAll();
        }
    }

    @Override
    public Set<Object> keySet() {
        return redisRepository.getKeys();
    }

    @Override
    public Collection<T> values() {
        return redisRepository.findAll().values();
    }

    @Override
    public Set<Entry<Object, T>> entrySet() {
        return redisRepository.findAll().entrySet();
    }
}
