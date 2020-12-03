package com.wesleyprado.trabalhouna.repositories;

import com.wesleyprado.trabalhouna.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Transactional(readOnly = true)
    public Client findByEmail(String email);

    @Transactional(readOnly = true)
    public Client findByEmailAndCpf(String email, String cpf);

}
