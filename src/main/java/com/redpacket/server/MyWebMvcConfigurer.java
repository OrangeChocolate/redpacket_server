package com.redpacket.server;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

@Configuration
public class MyWebMvcConfigurer {
	
	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver() {
		return new PageableHandlerMethodArgumentResolver(sortResolver());
	}

	@Bean
	public SortHandlerMethodArgumentResolver sortResolver() {
		return new SortHandlerMethodArgumentResolver();
	}
	
	@Bean
	public SpecificationArgumentResolver specificationResolver() {
		return new SpecificationArgumentResolver();
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
            }
            
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            	argumentResolvers.add(specificationResolver());
            	// see SpringDataWebConfiguration
            	// see http://docs.spring.io/spring-data/commons/docs/1.13.x/reference/html/#core.web
                argumentResolvers.add(sortResolver());
                argumentResolvers.add(pageableResolver());
            }
        };
    }
}