package com.master1.planningpoker.repositories;

import com.master1.planningpoker.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}
