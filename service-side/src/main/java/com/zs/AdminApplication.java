

package com.zs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 *
 *
 * @author Mark sunlightcs@gmail.com
 */
@SpringBootApplication
public class AdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
		System.out.println("启动成功");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminApplication.class);
	}
}