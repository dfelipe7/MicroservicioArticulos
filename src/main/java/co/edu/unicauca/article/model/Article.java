/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Clase que representa un artículo en el sistema de gestión de conferencias.
 * Cada artículo contiene información como el nombre, palabras clave, resumen,
 * ruta del archivo, y referencias a la conferencia y el autor.
 * 
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del artículo no puede estar vacío.")
    private String name;

    private String keywords;

    @NotBlank(message = "El resumen no puede estar vacío.")
    private String summary;

    private String filePath;

    private Long conferenceId;

    private Long autorId;

    /**
     * Obtiene el ID del artículo.
     * 
     * @return el ID del artículo
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del artículo.
     * 
     * @param id el nuevo ID del artículo
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del artículo.
     * 
     * @return el nombre del artículo
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del artículo.
     * 
     * @param name el nuevo nombre del artículo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene las palabras clave del artículo.
     * 
     * @return las palabras clave del artículo
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Establece las palabras clave del artículo.
     * 
     * @param keywords las nuevas palabras clave del artículo
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Obtiene el resumen del artículo.
     * 
     * @return el resumen del artículo
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Establece el resumen del artículo.
     * 
     * @param summary el nuevo resumen del artículo
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Obtiene la ruta del archivo del artículo.
     * 
     * @return la ruta del archivo
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Establece la ruta del archivo del artículo.
     * 
     * @param filePath la nueva ruta del archivo
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Obtiene el ID de la conferencia asociada con el artículo.
     * 
     * @return el ID de la conferencia
     */
    public Long getConferenceId() {
        return conferenceId;
    }

    /**
     * Establece el ID de la conferencia asociada con el artículo.
     * 
     * @param conferenceId el nuevo ID de la conferencia
     */
    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    /**
     * Obtiene el ID del autor que creó el artículo.
     * 
     * @return el ID del autor
     */
    public Long getAutorId() {
        return autorId;
    }

    /**
     * Establece el ID del autor que creó el artículo.
     * 
     * @param autorId el nuevo ID del autor
     */
    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }
}
