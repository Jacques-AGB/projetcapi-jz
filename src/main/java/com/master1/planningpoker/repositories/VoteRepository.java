package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @interface VoteRepository
 * @brief Interface du dépôt pour l'entité Vote, permettant l'accès aux données relatives aux votes des joueurs.
 *
 * Cette interface étend `JpaRepository` pour fournir des méthodes de gestion des entités `Vote` dans la base de données.
 * Elle permet de récupérer les votes par assignment ou par code de jeu.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Recherche tous les votes associés à un assignment spécifique.
     *
     * Cette méthode permet de récupérer tous les votes associés à un `Assignment` donné, identifié par son `id`.
     * Elle retourne une liste de votes associés à cet assignment.
     *
     * @param assignmentId L'identifiant de l'`Assignment` pour lequel récupérer les votes.
     * @return Une liste de votes associés à l'`Assignment` spécifié.
     */
    List<Vote> findByAssignmentId(Long assignmentId);

    /**
     * Recherche tous les votes associés à un jeu spécifié par son code.
     *
     * Cette méthode permet de récupérer tous les votes associés aux assignments d'un jeu spécifié par son `code`.
     * Elle retourne une liste de votes associés aux assignments de ce jeu.
     *
     * @param gameCode Le code du jeu pour lequel récupérer les votes.
     * @return Une liste de votes associés aux assignments du jeu spécifié.
     */
    @Query("SELECT v FROM Vote v WHERE v.assignment.game.code = :gameCode")
    List<Vote> findVotesByGameCode(@Param("gameCode") String gameCode);

    long countByAssignmentId(Long assignmentId);

    // Dans VoteRepository
    boolean existsByAssignmentIdAndPlayerId(Long assignmentId, Long playerId);

}
