package com.master1.planningpoker.repositories;


import com.master1.planningpoker.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByCode(String code);
}
