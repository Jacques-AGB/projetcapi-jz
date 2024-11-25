package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByPseudo(String pseudo);
    Optional<Player> findByPseudo(String pseudo);
}
