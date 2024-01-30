package com.github.lhmlyz.dynamicapigenerator.generator;

import com.github.lhmlyz.dynamicapigenerator.config.ApiConfiguration;
import com.github.lhmlyz.dynamicapigenerator.config.constants.ApplicationConstants;
import com.github.lhmlyz.dynamicapigenerator.model.Resource;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.Modifier;
import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class ControllerGenerator {

    private static final String PREFIX = "Controller";

    private final ApiConfiguration apiConfiguration;

    public void generate() {
        String packageName = apiConfiguration.getPackage();
        TypeSpec controller = createController();

        JavaFile file = JavaFile.builder(
                        String.join(".",
                                packageName, PREFIX.toLowerCase()), controller)
                .build();

        try {
            file.writeTo(new File(ApplicationConstants.JAVA_SOURCE_FOLDER));
        } catch (Exception ex) {
            log.error("class writing failed, {}", ex.getMessage());
        }
    }

    private TypeSpec createController() {
        Resource resource = getResource();
        String className = resource.getName().concat(PREFIX);
        String basePath = resource.getBasePath();
        MethodSpec method = createMethod();


        return TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(RestController.class)
                .addAnnotation(AnnotationSpec.builder(RequestMapping.class)
                        .addMember("value", basePath)
                        .build())
                .addMethod(method)
                .build();
    }

    private Resource getResource() {
        return apiConfiguration.getResource();
    }

    private MethodSpec createMethod() {
        return MethodSpec.methodBuilder("helloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(GetMapping.class)
                .returns(String.class)
                .addStatement("return $S", "Java Pet")
                .build();
    }
}
