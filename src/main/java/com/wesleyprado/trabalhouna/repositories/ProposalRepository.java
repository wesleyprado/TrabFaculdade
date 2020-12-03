package com.wesleyprado.trabalhouna.repositories;

import com.wesleyprado.trabalhouna.domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

}
