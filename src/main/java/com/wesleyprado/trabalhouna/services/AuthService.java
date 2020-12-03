package com.wesleyprado.trabalhouna.services;

import com.wesleyprado.trabalhouna.domain.Client;
import com.wesleyprado.trabalhouna.domain.Account;
import com.wesleyprado.trabalhouna.domain.ActivationToken;
import com.wesleyprado.trabalhouna.dto.RegisterPasswordDTO;
import com.wesleyprado.trabalhouna.dto.RequestTokenDTO;
import com.wesleyprado.trabalhouna.repositories.ClientRepository;
import com.wesleyprado.trabalhouna.repositories.AccountRepository;
import com.wesleyprado.trabalhouna.repositories.ActivationTokenRepository;
import com.wesleyprado.trabalhouna.services.exception.InvalidTokenException;
import com.wesleyprado.trabalhouna.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Random;

@Service
public class AuthService {

    private final ClientRepository clientRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AccountRepository accountRepository;

    private final EmailService emailService;

    private final ActivationTokenRepository activationTokenRepository;

    private final AccountService accountService;

    private Random rand = new Random();

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    public AuthService(ClientRepository clientRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       EmailService emailService, ActivationTokenRepository activationTokenRepository,
                       AccountRepository accountRepository, AccountService accountService) {
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
        this.activationTokenRepository = activationTokenRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public void solicitarToken(RequestTokenDTO requestTokenDTO) {
        Client client = clientRepository.findByEmailAndCpf(requestTokenDTO.getEmail(), requestTokenDTO.getCpf());

        if(client == null){
            throw new ObjectNotFoundException("Cliente não encontrado");
        }

        ActivationToken activationToken = new ActivationToken();
        activationToken.setAccount(client.getProposal().getAccount());
        activationToken.setUsed(false);
        activationToken.setId(null);
        activationToken.setToken(newToken(6));
        activationToken.setExpirationDate(
                LocalDateTime.now().plus(expiration, ChronoField.MILLI_OF_DAY.getBaseUnit())
        );

        activationToken = activationTokenRepository.save(activationToken);
        emailService.registerNewPassword(activationToken);
    }

    public void savePassword(RegisterPasswordDTO registerPasswordDTO) {
        Account account = accountRepository.findByProposalClientEmailAndProposalClientCpf(
                registerPasswordDTO.getEmail(), registerPasswordDTO.getCpf());

        if(account == null){
            throw new ObjectNotFoundException("Account not found");
        }

        ActivationToken activationToken = activationTokenRepository.findByAccountIdAndToken(account.getId(),
                registerPasswordDTO.getToken());

        if(activationToken == null
                || activationToken.getUsed()
                || activationToken.getExpirationDate().isBefore(LocalDateTime.now())
        ) {
            throw new InvalidTokenException("Invalid activation token");
        }

        activationToken.setUsed(true);
        activationTokenRepository.save(activationToken);

        account.setPassword(bCryptPasswordEncoder.encode(registerPasswordDTO.getPassword()));

        accountService.updatePassword(account);
        emailService.updatedPassword(account);
    }

    private String newToken(int size) {
        char[] randomToken = new char[size];
        for( int i=0; i<size; i++){
            randomToken[i] = randomChar();
        }
        return new String(randomToken);
    }

    private char randomChar() {
        int option = rand.nextInt(3);
        if( option == 0 ){
            return (char) (rand.nextInt(10) + 48);
        } else if( option == 1){
            return (char) (rand.nextInt(26) + 65);
        } else {
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
