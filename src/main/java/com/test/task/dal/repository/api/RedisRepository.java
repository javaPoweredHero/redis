package com.test.task.dal.repository.api;

import com.test.task.dal.domain.api.RedisEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisRepository<T extends RedisEntity> {

    void save(T entity);

    void saveAll(Map<?, ? extends T> map);

    T findOne(Object id);

    Map<Object, T> findAll();

    void delete(Object id);

    Long size();

    Boolean hasKey(Object key);

    Set<Object> getKeys();

    List<T> getValues();

    void removeAll();
}
