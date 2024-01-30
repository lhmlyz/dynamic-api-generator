package com.github.lhmlyz.dynamicapigenerator.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Resource {

    @NotBlank
    private String name;

    @NotBlank
    private String basePath;

}
