package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @interface RuleRepository
 * @brief Interface du dépôt pour l'entité Rule, permettant l'accès aux données relatives aux règles.
 *
 * Cette interface étend `JpaRepository` pour fournir des méthodes de gestion des entités `Rule` dans la base de données.
 * Elle permet de vérifier l'existence d'une règle par son nom.
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    /**
     * Vérifie si une règle existe déjà avec un nom donné.
     *
     * Cette méthode permet de vérifier si une règle avec un nom donné existe déjà dans la base de données.
     * Elle retourne un booléen indiquant si une règle avec ce nom existe ou non.
     *
     * @param name Le nom de la règle à vérifier.
     * @return `true` si une règle avec ce nom existe, `false` sinon.
     */
    boolean existsByName(String name);
}
