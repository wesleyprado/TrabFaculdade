package com.wesleyprado.trabalhouna.services;

import com.wesleyprado.trabalhouna.domain.Account;
import com.wesleyprado.trabalhouna.domain.Client;
import com.wesleyprado.trabalhouna.domain.ActivationToken;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void welcomeNewClient(Client client);
    void insistCustomerConfirmation(Client client);
    void simpleEmail(SimpleMailMessage simpleMailMessage);
    void registerNewPassword(ActivationToken activationToken);
    void updatedPassword(Account account);
}
