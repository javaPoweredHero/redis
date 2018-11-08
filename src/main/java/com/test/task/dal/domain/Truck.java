package com.test.task.dal.domain;

import com.test.task.dal.domain.api.RedisEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Truck implements RedisEntity, Serializable {

    private String key;
    private String name;
    private Integer number;
    private Integer weight;
}
