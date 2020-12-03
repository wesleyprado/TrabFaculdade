package com.wesleyprado.trabalhouna.services;

import com.wesleyprado.trabalhouna.domain.Account;
import com.wesleyprado.trabalhouna.domain.enums.Role;
import com.wesleyprado.trabalhouna.repositories.AccountRepository;
import com.wesleyprado.trabalhouna.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String branchAccount) throws UsernameNotFoundException {
        Account account = accountRepository.findByBranchNumberAndAccountNumber(branchAccount.substring(0, 4), branchAccount.substring(4, 12));

        if( account == null ){
            throw new UsernameNotFoundException(branchAccount);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.CLIENT);
        return new UserSS(account.getId(), branchAccount, account.getPassword(), roles);
    }

}
