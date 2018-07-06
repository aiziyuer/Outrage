package com.aiziyuer.outrage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class CoreApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoreApplication.class);
	}

	public static void main(String[] args) {

		try {

			SpringApplication.run(CoreApplication.class, args);

		} catch (RuntimeException e) {

			if (StringUtils.contains(e.getClass().toString(), "SilentExitException"))
				log.info("devtools enable exit main thread normally.");
			else
				log.error(e);
		}
	}

}
