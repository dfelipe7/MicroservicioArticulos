/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración para definir los beans de la aplicación.
 * Se encarga de proporcionar una instancia de RestTemplate para realizar llamadas HTTP.
 *
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
