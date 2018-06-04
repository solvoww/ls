package pl.poznan.ww.ls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class LsServer {

	public static void main(String[] args) {
		SpringApplication.run(LsServer.class, args);
	}
        
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedOrigins("http://localhost:4200");
                    registry.addMapping("/{path}").allowedOrigins("http://localhost:4200");
                }
            };
        }
        
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(DispatcherServletInitializer.class, LsController.class,LsServer.class);
        }
        
}
