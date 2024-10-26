/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.presentacion;

/**
 *
 * @author Unicauca
 */
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
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${session.service.url}")
    private String sessionServiceUrl;

    //@Value("${conference.service.url}")
    //private String conferenceServiceUrl; // URL del microservicio de conferencias
@PostMapping
public ResponseEntity<String> createArticle(@RequestBody Article article, @RequestParam Long userId) {
    // Llamar al microservicio de sesión para obtener la información del usuario
    ResponseEntity<UserDTO> response = restTemplate.getForEntity(sessionServiceUrl + "/api/users/" + userId, UserDTO.class);

    if (response.getStatusCode().is2xxSuccessful()) {
        UserDTO loggedUser = response.getBody();

        // Verificar que el usuario tenga el rol de organizador
        if (!"autor".equals(loggedUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo los autores pueden crear articulos.");
        }

        // Asignar el userId como organizerId a la conferencia
        article.setAutorId(userId);  // Asigna el organizador

        // Crear conferencia
        Article createdArticle = articleService.createConference(article);
        return ResponseEntity.status(HttpStatus.CREATED).body("Articulo creado con éxito.");
    } else {
        return ResponseEntity.status(response.getStatusCode()).body("Error al obtener información del usuario.");
    }
}


    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

  @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> conference = articleService.getArticleById(id);
        return conference.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

@PutMapping("/{id}")
public ResponseEntity<String> updateArticle(@PathVariable Long id, @RequestBody Article conference, @RequestParam Long userId) {
    ResponseEntity<UserDTO> response = restTemplate.getForEntity(sessionServiceUrl + "/api/users/" + userId, UserDTO.class);

    if (response.getStatusCode().is2xxSuccessful()) {
        UserDTO loggedUser = response.getBody();

        if (!"autor".equals(loggedUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo los autores pueden actualizar articulos.");
        }

        // Verificar si el articulo existe
        Optional<Article> existingArticle = articleService.getArticleById(id);
        if (existingArticle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Articulo no encontrado.");
        }

        // Verificar que el usuario sea el organizador que creó la conferencia
        if (!existingArticle.get().getAutorId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo el autor que creó la conferencia puede actualizarla.");
        }

        // Actualizar conferencia
        articleService.updateConference(id, conference);
        return ResponseEntity.ok("Conferencia actualizada exitosamente.");
    } else {
        return ResponseEntity.status(response.getStatusCode()).body("Error al obtener información del usuario.");
    }
}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, @RequestParam Long userId) {
        // Llamar al microservicio de sesiones para obtener la información del usuario
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(sessionServiceUrl + "/api/users/" + userId, UserDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            UserDTO loggedUser = response.getBody();

            // Verificar si el usuario es un autor
            if (!"autor".equals(loggedUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo los autores pueden eliminar artículos.");
            }

            articleService.deleteArticle(id);
            return ResponseEntity.ok("Artículo eliminado exitosamente.");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error al obtener información del usuario.");
        }
    }
}
