package com.test.task.dal.domain;

import com.test.task.dal.domain.api.RedisEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@RedisHash("Truck")
public class Truck implements RedisEntity, Serializable {

    @Id private String key;
    @Indexed private String name;
    private Integer number;
    private Integer weight;
}
