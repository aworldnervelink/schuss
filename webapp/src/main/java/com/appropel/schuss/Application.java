package com.appropel.schuss;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * Primary Spring Boot class.
 * http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html
 */

@SpringBootApplication
@ImportResource("classpath:spring.xml")
@Configuration
@EnableAutoConfiguration
@EnableScheduling
//@EnableWebSecurity
@ComponentScan
@SuppressWarnings("PMD")
public class Application extends SpringBootServletInitializer
{
    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application)
    {
        return application.sources(Application.class);
    }

    /**
     * Main webapp method.
     *
     * @param args an array of running parameters.
     */
    public static void main(final String[] args)
    {
        SpringApplication.run(Application.class, args);
        // Set default time zone, so all new Date() objects will use GMT time.
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Return custom VX Jackson ObjectMapper.
     *
     * @return ObjectMapper.
     */
    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }

    /**
     * Returns {@link RestTemplate} object build on {@link RestTemplateBuilder}.
     *
     * @param builder to build {@link RestTemplate}
     * @return {@link RestTemplate}
     */
//    @Bean
//    public RestTemplate restTemplate(final RestTemplateBuilder builder)
//    {
//        return builder.build();
//    }
}
