package com.redpacket.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.redpacket.server.wechat.config.WechatMpProperties;

@SpringBootApplication
@EnableConfigurationProperties({WechatMpProperties.class, ApplicationProperties.class})
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	// https://spring.io/guides/gs/consuming-rest/
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	// https://stackoverflow.com/questions/24339990/how-to-convert-a-multipart-file-to-file
//	@Bean(name = "multipartResolver")
//    public CommonsMultipartResolver createMultipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("utf-8");
//        resolver.setMaxInMemorySize(0);
//        return resolver;
//    }
	// https://stackoverflow.com/questions/39007726/spring-boot-multipartfile-always-null
	@Bean
	public MultipartResolver multipartResolver() {
	    return new CommonsMultipartResolver();
	}
}
