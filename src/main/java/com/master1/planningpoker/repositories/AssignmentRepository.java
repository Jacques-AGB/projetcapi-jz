package com.master1.planningpoker.repositories;

import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    boolean existsByLibelle(String libelle);
    List<Assignment> findAllByGameId(Long gameId);
    @Query("SELECT a FROM Assignment a WHERE a.game.code = :gameCode")
    List<Assignment> findAssignmentsByGameCode(@Param("gameCode") String gameCode);

}
