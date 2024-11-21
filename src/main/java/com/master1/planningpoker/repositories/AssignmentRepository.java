package com.master1.planningpoker.repositories;

import com.master1.planningpoker.dtos.responses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    boolean existsByLibelle(String libelle);
    List<Assignment> findByGameId(Long gameId);

}
