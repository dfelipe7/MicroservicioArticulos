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
import co.edu.unicauca.article.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
/*
    @Autowired
    private ArticleRepository articleRepository; // Suponiendo que tienes un repositorio para los artículos

   public Article createArticle(Article article) {
    // Asegúrate de que la lógica de creación del artículo sea correcta
    if (article == null) {
        throw new IllegalArgumentException("El artículo no puede ser nulo");
    }
    // Otras validaciones según sea necesario
    return articleRepository.save(article);
}


    public List<Article> getAllArticles() {
        return articleRepository.findAll(); // Obtener todos los artículos
    }

    public Article getArticleById(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.orElse(null); // Devolver el artículo o null si no existe
    }

    public void updateArticle(Long id, Article article) {
        // Asegurarse de que el artículo existe antes de actualizarlo
        if (articleRepository.existsById(id)) {
            article.setId(id); // Establecer el ID del artículo para la actualización
            articleRepository.save(article); // Guardar el artículo actualizado
        } else {
            throw new RuntimeException("Artículo no encontrado"); // Lanzar una excepción o manejar el error según sea necesario
        }
    }

    public void deleteArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id); // Eliminar el artículo si existe
        } else {
            throw new RuntimeException("Artículo no encontrado"); // Lanzar una excepción o manejar el error según sea necesario
        }
    }*/
    
    
    
    @Autowired
    private ArticleRepository articleRepository;

    public Article createConference(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article updateConference(Long id, Article article) {
        // Verificar si existe la conferencia
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conference not found"));
        
        // Actualizar propiedades
        existingArticle.setName(article.getName());
        existingArticle.setKeywords(article.getKeywords());
        existingArticle.setSummary(article.getSummary());
        existingArticle.setFilePath(article.getFilePath());
     
        
        return articleRepository.save(existingArticle);
    }

    public void deleteArticle(Long id) {
        // Verificar si existe la conferencia
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conference not found");
        }
        articleRepository.deleteById(id);
    }

}
