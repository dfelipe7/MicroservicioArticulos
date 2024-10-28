/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.article.dao;

/**
 * Repositorio para gestionar las operaciones de la entidad Article en la base de datos.
 * Extiende JpaRepository para proporcionar métodos CRUD predefinidos.
 *
 * @author Daniel Muñoz
 * @author Jesus Iles
 * @author Esteban Martinez
 * @author Felipe Armero
 */
import co.edu.unicauca.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    
}
