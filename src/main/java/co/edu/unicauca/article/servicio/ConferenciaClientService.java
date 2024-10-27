/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.servicio;

import co.edu.unicauca.article.dto.ConferenceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Unicauca
 */
@Service
public class ConferenciaClientService {

    private final RestTemplate restTemplate;
    private final String conferenciasUrl = "http://localhost:8085/api/conferences";

    public ConferenciaClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConferenceDTO obtenerConferencia(Long conferenciaId) {
        String url = conferenciasUrl + "/" + conferenciaId;
        return restTemplate.getForObject(url, ConferenceDTO.class);
    }
}
