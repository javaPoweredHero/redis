package com.test.task.dal.repository.impl;

import com.test.task.dal.domain.api.RedisEntity;
import com.test.task.dal.repository.api.RedisRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisRepositoryImpl<T extends RedisEntity> implements RedisRepository<T> {

    private RedisTemplate<Object, Object> redisTemplate;
    private final String OBJECT_KEY;

    public RedisRepositoryImpl(Class<T> clazz, RedisTemplate<Object, Object> redisTemplate) {
        OBJECT_KEY = clazz.getSimpleName();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(T entity) {
        this.redisTemplate.opsForHash().put(OBJECT_KEY, entity.getKey(), entity);
    }

    @Override
    public void saveAll(Map<?, ? extends T> map) {
        this.redisTemplate.opsForHash().putAll(OBJECT_KEY, map);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findOne(Object id) {
        return (T) this.redisTemplate.opsForHash().get(OBJECT_KEY, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, T> findAll() {
        return new HashMap<Object, T>((Map) this.redisTemplate.opsForHash().entries(OBJECT_KEY));
    }

    @Override
    public void delete(Object id) {
        this.redisTemplate.opsForHash().delete(OBJECT_KEY, id);
    }

    @Override
    public Long size() {
        return redisTemplate.opsForHash().size(OBJECT_KEY);
    }

    @Override
    public Boolean hasKey(Object key) {
        return redisTemplate.opsForHash().hasKey(OBJECT_KEY, key);
    }

    @Override
    public Set<Object> getKeys() {
        return redisTemplate.opsForHash().keys(OBJECT_KEY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getValues() {
        return (List<T>) (Object) redisTemplate.opsForHash().values(OBJECT_KEY);
    }

    @Override
    public void removeAll() {
        redisTemplate.opsForHash().delete(OBJECT_KEY, this.getKeys().toArray());
    }
}