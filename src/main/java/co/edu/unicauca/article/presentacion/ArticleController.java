/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.presentacion;

import co.edu.unicauca.article.dto.ConferenceDTO;
import co.edu.unicauca.article.dto.UserDTO;
import co.edu.unicauca.article.model.Article;
import co.edu.unicauca.article.servicio.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 * Controlador para manejar las solicitudes HTTP relacionadas con los artículos.
 * Proporciona métodos para crear, obtener, actualizar y eliminar artículos.
 *
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService; // Servicio para la gestión de artículos

    @Autowired
    private RestTemplate restTemplate; // Cliente REST para realizar peticiones HTTP

    @Value("${session.service.url}")
    private String sessionServiceUrl; // URL del microservicio de sesión

    @Value("${conference.service.url}")
    private String conferenceServiceUrl; // URL del microservicio de conferencias

    /**
     * Crea un nuevo artículo.
     *
     * @param article El objeto Article a crear.
     * @param userId El ID del usuario que crea el artículo.
     * @param conferenceId El ID de la conferencia asociada al artículo.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody Article article, @RequestParam Long userId, @RequestParam Long conferenceId) {
        try {
            // Verificar que la conferencia exista
            ResponseEntity<ConferenceDTO> conferenceResponse = restTemplate.getForEntity(conferenceServiceUrl + "/api/conferences/" + conferenceId, ConferenceDTO.class);
            if (!conferenceResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La conferencia especificada no existe.");
            }

            // Llamar al microservicio de sesión para obtener la información del usuario
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(sessionServiceUrl + "/api/users/" + userId, UserDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                UserDTO loggedUser = response.getBody();

                // Verificar que el usuario tenga el rol de autor
                if (!"Autor".equals(loggedUser.getRole())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo los autores pueden crear artículos.");
                }

                // Asignar el userId como autorId al artículo y el conferenceId como referencia a la conferencia
                article.setAutorId(userId);
                article.setConferenceId(conferenceId);

                // Crear artículo
                Article createdArticle = articleService.createArticle(article, conferenceId);
                return ResponseEntity.status(HttpStatus.CREATED).body("Artículo creado con éxito.");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Error al obtener información del usuario.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para registrar el error en los logs del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los artículos.
     *
     * @return ResponseEntity con la lista de artículos.
     */
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    /**
     * Obtiene un artículo por su ID.
     *
     * @param id El ID del artículo a obtener.
     * @return ResponseEntity con el artículo encontrado o un estado NOT_FOUND si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        return article.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Actualiza un artículo existente.
     *
     * @param id El ID del artículo a actualizar.
     * @param article El objeto Article con los datos a actualizar.
     * @param userId El ID del usuario que realiza la actualización.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable Long id, @RequestBody Article article, @RequestParam Long userId) {
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(sessionServiceUrl + "/api/users/" + userId, UserDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            UserDTO loggedUser = response.getBody();

            // Verificar que el usuario tenga el rol de autor
            if (!"Autor".equals(loggedUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo los autores pueden actualizar artículos.");
            }

            Optional<Article> existingArticle = articleService.getArticleById(id);
            if (existingArticle.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artículo no encontrado.");
            }

            // Verificar que el usuario es el autor del artículo
            if (!existingArticle.get().getAutorId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo el autor que creó el artículo puede actualizarlo.");
            }

            articleService.updateArticle(id, article);
            return ResponseEntity.ok("Artículo actualizado exitosamente.");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error al obtener información del usuario.");
        }
    }

    /**
     * Elimina un artículo por su ID.
     *
     * @param id El ID del artículo a eliminar.
     * @param userId El ID del usuario que realiza la eliminación.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, @RequestParam Long userId) {
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(sessionServiceUrl + "/api/users/" + userId, UserDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            UserDTO loggedUser = response.getBody();

            // Verificar que el usuario tenga el rol de autor
            if (!"Autor".equals(loggedUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo los autores pueden eliminar artículos.");
            }

            Optional<Article> existingArticle = articleService.getArticleById(id);
            if (existingArticle.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artículo no encontrado.");
            }

            // Verificar que el usuario es el autor del artículo
            if (!existingArticle.get().getAutorId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo el autor que creó el artículo puede eliminarlo.");
            }

            articleService.deleteArticle(id);
            return ResponseEntity.ok("Artículo eliminado exitosamente.");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error al obtener información del usuario.");
        }
    }
}
