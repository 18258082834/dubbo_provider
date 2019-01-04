package com.ys.springboot_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@ImportResource("classpath:dubbo.xml")
@Controller
public class SpringbootTestApplication {
	@Resource
	private Environment environment;
	@ResponseBody
	@GetMapping("/index")
	String index(){
		String a = environment.getProperty("name");
		return "哈哈哈啊，jenkins总算搭建完了@！！！！！,name:"+environment.getProperty("name");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTestApplication.class, args);
	}
}
