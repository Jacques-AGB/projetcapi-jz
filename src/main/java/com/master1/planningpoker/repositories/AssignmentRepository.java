package com.master1.planningpoker.repositories;

import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @interface AssignmentRepository
 * @brief Interface du dépôt pour l'entité Assignment, permettant l'accès aux données relatives aux assignations (tâches).
 *
 * Cette interface étend `JpaRepository` pour fournir des méthodes de gestion des entités `Assignment` dans la base de données.
 * Elle inclut des méthodes pour récupérer des assignations en fonction de différents critères, comme le libellé ou le code de jeu.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /**
     * Vérifie si une assignation (tâche) existe déjà avec un libellé donné.
     * @param libelle Le libellé de l'assignation à vérifier.
     * @return True si une assignation existe déjà avec ce libellé, false sinon.
     */
    boolean existsByLibelle(String libelle);

    /**
     * Récupère toutes les assignations liées à un jeu spécifié par son ID.
     *
     * @param gameId L'ID du jeu pour lequel récupérer les assignations.
     * @return Une liste d'assignations (tâches) liées à ce jeu.
     */
    List<Assignment> findAllByGameId(Long gameId);

    /**
     * Récupère toutes les assignations liées à un jeu spécifié par son code.
     * Cette méthode utilise une requête personnalisée (JPQL) pour récupérer les assignations associées au code du jeu.
     *
     * @param gameCode Le code du jeu pour lequel récupérer les assignations.
     * @return Une liste d'assignations (tâches) liées à ce code de jeu.
     */
    @Query("SELECT a FROM Assignment a WHERE a.game.code = :gameCode")
    List<Assignment> findAssignmentsByGameCode(@Param("gameCode") String gameCode);

}
