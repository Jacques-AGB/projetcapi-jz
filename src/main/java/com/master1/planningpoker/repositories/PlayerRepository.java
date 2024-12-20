package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @interface PlayerRepository
 * @brief Interface du dépôt pour l'entité Player, permettant l'accès aux données relatives aux joueurs.
 *
 * Cette interface étend `JpaRepository` pour fournir des méthodes de gestion des entités `Player` dans la base de données.
 * Elle inclut des méthodes permettant de vérifier l'existence d'un joueur par pseudo et de récupérer un joueur en fonction de son pseudo.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Vérifie si un joueur existe déjà avec un pseudo donné.
     *
     * Cette méthode permet de vérifier si un joueur avec un pseudo donné existe déjà dans la base de données.
     * Elle retourne un booléen indiquant si un joueur avec ce pseudo existe ou non.
     *
     * @param pseudo Le pseudo du joueur à vérifier.
     * @return `true` si un joueur avec ce pseudo existe, `false` sinon.
     */
    boolean existsByPseudo(String pseudo);

    /**
     * Récupère un joueur en fonction de son pseudo.
     *
     * Cette méthode permet de rechercher un joueur à partir de son pseudo unique. Si un joueur avec ce pseudo est trouvé, la méthode retourne un `Optional` contenant le joueur. Si aucun joueur n'est trouvé, la méthode retourne un `Optional` vide.
     *
     * @param pseudo Le pseudo du joueur à rechercher.
     * @return Un `Optional` contenant le joueur trouvé, ou un `Optional` vide si aucun joueur n'a été trouvé avec ce pseudo.
     */
    Optional<Player> findByPseudo(String pseudo);

    List<Player> findByGameId(Long gameId);
}
