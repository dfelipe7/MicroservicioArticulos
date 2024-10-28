/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.servicio;

import co.edu.unicauca.article.dto.ConferenceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio para la interacción con el microservicio de conferencias.
 * Proporciona métodos para obtener información sobre conferencias.
 *
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
@Service
public class ConferenciaClientService {

    private final RestTemplate restTemplate; // Cliente REST para realizar peticiones HTTP
    private final String conferenciasUrl = "http://localhost:8085/api/conferences"; // URL base para las conferencias

    /**
     * Constructor de ConferenciaClientService.
     *
     * @param restTemplate Instancia de RestTemplate utilizada para realizar las peticiones HTTP.
     */
    public ConferenciaClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene una conferencia por su ID.
     *
     * @param conferenciaId El ID de la conferencia que se va a obtener.
     * @return Un objeto ConferenceDTO que representa la conferencia.
     */
    public ConferenceDTO obtenerConferencia(Long conferenciaId) {
        String url = conferenciasUrl + "/" + conferenciaId; // Construir la URL de la conferencia
        return restTemplate.getForObject(url, ConferenceDTO.class); // Realizar la solicitud GET
    }
}
