package com.wesleyprado.trabalhouna.repositories;

import com.wesleyprado.trabalhouna.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

}
