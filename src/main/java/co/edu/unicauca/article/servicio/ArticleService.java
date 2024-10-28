/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.servicio;

/**
 * Servicio para la gestión de artículos.
 * Proporciona métodos para crear, obtener, actualizar y eliminar artículos.
 *
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
import co.edu.unicauca.article.dao.ArticleRepository;
import co.edu.unicauca.article.dto.ConferenceDTO;
import co.edu.unicauca.article.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ConferenciaClientService conferenciaClientService;

    /**
     * Crea un nuevo artículo asociado a una conferencia específica.
     * 
     * @param article El objeto Article que se va a crear.
     * @param conferenciaId El ID de la conferencia a la que se asocia el artículo.
     * @return El artículo creado.
     * @throws IllegalStateException si la conferencia no existe.
     */
    public Article createArticle(Article article, Long conferenciaId) {
        // Verificar que la conferencia exista
        ConferenceDTO conferencia = conferenciaClientService.obtenerConferencia(conferenciaId);

        if (conferencia == null) {
            throw new IllegalStateException("La conferencia no es válida o no existe");
        }
        
        // Asociar la conferencia al artículo
        article.setConferenceId(conferenciaId);
        return articleRepository.save(article);
    }

    /**
     * Obtiene todos los artículos.
     * 
     * @return Una lista de todos los artículos.
     */
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Obtiene un artículo por su ID.
     * 
     * @param id El ID del artículo que se va a obtener.
     * @return Un objeto Optional que contiene el artículo si se encuentra, o vacío si no.
     */
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    /**
     * Actualiza un artículo existente.
     * 
     * @param id El ID del artículo que se va a actualizar.
     * @param article El objeto Article que contiene los nuevos datos.
     * @return El artículo actualizado.
     * @throws ResourceNotFoundException si el artículo no existe.
     */
    public Article updateArticle(Long id, Article article) {
        // Verificar si existe el artículo
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        
        // Actualizar propiedades
        existingArticle.setName(article.getName());
        existingArticle.setKeywords(article.getKeywords());
        existingArticle.setSummary(article.getSummary());
        existingArticle.setFilePath(article.getFilePath());
        
        return articleRepository.save(existingArticle);
    }

    /**
     * Elimina un artículo por su ID.
     * 
     * @param id El ID del artículo que se va a eliminar.
     * @throws ResourceNotFoundException si el artículo no existe.
     */
    public void deleteArticle(Long id) {
        // Verificar si existe el artículo
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article not found");
        }
        articleRepository.deleteById(id);
    }
}
