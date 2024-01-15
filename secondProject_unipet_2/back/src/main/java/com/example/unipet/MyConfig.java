package com.example.unipet;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    //@Value("${app.file.storage.mapping}")
    String currentDirectory = System.getProperty("user.dir");
    // String location="file:///c:/kosastudy/springedu/src/main/resources/static/images/";
    String correctedPath = currentDirectory.replace("\\", "/");
    String location = "file:///" + correctedPath + "/back/src/main/resources/static/img/mypage/upload/";

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        System.out.println("=================== : " +location);
        registry.addResourceHandler("/img/mypage/upload/**").addResourceLocations(location);
    }
}


//Hospital information