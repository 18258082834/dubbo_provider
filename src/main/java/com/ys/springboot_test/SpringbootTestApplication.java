package com.ys.springboot_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@ImportResource("classpath:dubbo.xml")
public class SpringbootTestApplication {
	@Resource
	private Environment environment;
	@ResponseBody
	String index(){
		String a = environment.getProperty("name");
		return "name:"+environment.getProperty("name");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTestApplication.class, args);
	}
}