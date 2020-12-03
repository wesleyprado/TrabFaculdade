package com.wesleyprado.trabalhouna.services;

import com.wesleyprado.trabalhouna.domain.Client;
import com.wesleyprado.trabalhouna.repositories.ClientRepository;
import com.wesleyprado.trabalhouna.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repo;

    @Autowired
    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public Client find(Integer id){
        Optional<Client> client = repo.findById(id);
        return client.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Client.class.getName()
        ));
    }

}
