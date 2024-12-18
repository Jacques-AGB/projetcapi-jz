package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @interface GameRepository
 * @brief Interface du dépôt pour l'entité Game, permettant l'accès aux données relatives aux jeux.
 *
 * Cette interface étend `JpaRepository` pour fournir des méthodes de gestion des entités `Game` dans la base de données.
 * Elle inclut des méthodes permettant de récupérer des jeux en fonction de leur code.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    /**
     * Récupère un jeu à partir de son code unique.
     *
     * Cette méthode permet de rechercher un jeu en utilisant son code. Si aucun jeu avec ce code n'est trouvé, la méthode retourne un `Optional` vide.
     *
     * @param code Le code unique du jeu à rechercher.
     * @return Un `Optional` contenant le jeu trouvé, ou un `Optional` vide si aucun jeu n'a été trouvé avec ce code.
     */
    Optional<Game> findByCode(String code);

}
