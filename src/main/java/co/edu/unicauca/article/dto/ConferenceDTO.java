/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.dto;

/**
 * Clase de transferencia de datos (DTO) que representa la información básica de una conferencia.
 * Contiene el ID y el nombre de la conferencia.
 *
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
public class ConferenceDTO {
    private Long id; // ID de la conferencia
    private String name; // Nombre de la conferencia

    /**
     * Constructor de la clase ConferenceDTO.
     *
     * @param id   el ID de la conferencia
     * @param name el nombre de la conferencia
     */
    public ConferenceDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Obtiene el ID de la conferencia.
     *
     * @return el ID de la conferencia
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la conferencia.
     *
     * @param id el nuevo ID de la conferencia
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la conferencia.
     *
     * @return el nombre de la conferencia
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de la conferencia.
     *
     * @param name el nuevo nombre de la conferencia
     */
    public void setName(String name) {
        this.name = name;
    }
}
