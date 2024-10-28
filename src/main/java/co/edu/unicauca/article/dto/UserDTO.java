/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.dto;

/**
 * Clase de transferencia de datos (DTO) que representa la información básica de un usuario.
 * Contiene el ID, nombre de usuario y rol del usuario en el sistema.
 * 

 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
public class UserDTO {
    private Long id; // ID del usuario
    private String username; // Nombre de usuario
    private String role; // Rol del usuario (ej. autor, organizador)

    /**
     * Constructor de la clase UserDTO.
     * 
     * @param id        el ID del usuario
     * @param username  el nombre de usuario
     * @param role      el rol del usuario
     */
    public UserDTO(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    /**
     * Obtiene el ID del usuario.
     * 
     * @return el ID del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     * 
     * @param id el nuevo ID del usuario
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return el nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param username el nuevo nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @return el rol del usuario
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param role el nuevo rol del usuario
     */
    public void setRole(String role) {
        this.role = role;
    }
}
