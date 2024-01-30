package com.github.lhmlyz.dynamicapigenerator.config;

import com.github.lhmlyz.dynamicapigenerator.model.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(value = "api")
public class ApiConfiguration {

    private String name;
    private String group;

    private @Valid Resource resource;

    public String getPackage() {
        return String.join(".", group, name);
    }

}
