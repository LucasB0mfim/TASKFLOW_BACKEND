package br.com.fatto.taskflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe responsável por configurar o suporte a CORS no sistema.
 * Permite o acesso de origens externas, necessário em sistemas distribuídos.
 * 
 * @author Lucas Bomfim
 */

@Configuration
public class CorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOriginPatterns("*") // Permitir todas as origens
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
						.allowedHeaders("*") // Permitir todos os headers
						.allowCredentials(true); // Permitir credenciais (cookies, autenticação, etc.)
			}
		};
	}
}
