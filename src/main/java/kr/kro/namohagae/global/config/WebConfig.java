package kr.kro.namohagae.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("css", MediaType.TEXT_PLAIN)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

}
