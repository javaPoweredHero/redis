package com.test.task;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@ConfigurationProperties(prefix = "redis")
@Data
@Validated
public class TaskApplicationConfig {

    @NotNull
    private List<String> nodes;
    @NotNull
    private Integer maxRedirects;
}
