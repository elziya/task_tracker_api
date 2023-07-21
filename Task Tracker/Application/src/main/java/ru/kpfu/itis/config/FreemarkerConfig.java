package ru.kpfu.itis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class FreemarkerConfig {

    public static final String FREEMARKER_EXTENSION = ".ftlh";

    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("classpath:/mails/");
        return configurer;
    }
}

