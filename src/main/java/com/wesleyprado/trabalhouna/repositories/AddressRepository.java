package com.wesleyprado.trabalhouna.repositories;

import com.wesleyprado.trabalhouna.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
