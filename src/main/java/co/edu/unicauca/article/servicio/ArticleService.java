/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.article.servicio;

/**
 *
 * @author Unicauca
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

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

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

    public void deleteArticle(Long id) {
        // Verificar si existe el artículo
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article not found");
        }
        articleRepository.deleteById(id);
    }
}