package com.wesleyprado.trabalhouna.repositories;

import com.wesleyprado.trabalhouna.domain.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Integer> {

    @Transactional(readOnly = true)
    public ActivationToken findByAccountIdAndToken(Integer id, String token);

}
