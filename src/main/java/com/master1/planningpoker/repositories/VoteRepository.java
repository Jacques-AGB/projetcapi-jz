package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByAssignmentId(Long assignmentId);
    @Query("SELECT v FROM Vote v WHERE v.assignment.game.code = :gameCode")
    List<Vote> findVotesByGameCode(@Param("gameCode") String gameCode);
}