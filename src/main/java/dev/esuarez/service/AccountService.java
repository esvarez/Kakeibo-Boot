package dev.esuarez.service;

import dev.esuarez.error.account.AccountNotFoundException;
import dev.esuarez.error.accounttype.AccountTypeNotFoundException;
import dev.esuarez.error.user.UserNotFoundException;
import dev.esuarez.model.Account;
import dev.esuarez.repository.AccountRepository;
import dev.esuarez.repository.AccountTypeRepository;
import dev.esuarez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public List<Account> getAllAccountsByUserId(Long userId){
        return accountRepository.findByUserId(userId);
        //return accountRepository.findAll();
    }

    public Account findAccountById(Long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account createAccount(Long userId, Account account){
        Long accountTypeId = account.getAccountType().getId();
        return userRepository.findById(userId)
                .map(user -> {
                    return accountTypeRepository.findById(accountTypeId)
                            .map(accountType -> {
                                BigDecimal amount = account.getAmount();

                                if (amount == null)
                                    account.setAmount(BigDecimal.ZERO);
                                account.setUser(user);
                                account.setAccountType(accountType);
                                return accountRepository.save(account);
                            }).orElseThrow(() -> new AccountTypeNotFoundException(accountTypeId));
                }).orElseThrow(() -> new UserNotFoundException(userId));
    }
/*
    public Account saveOrUpdateAccount(Long userId, Long accountId, Account account){
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }


        /*
        return accountRepository.findById(accountId)
                .map( account1 -> {
                    account1.setName(account.getName());
                    account1.setDescription(account.getDescription());
                    return accountRepository.save(account1);
                }).orElseGet(() -> {
                    account.setId(accountId);
                    return accountRepository.save(account);
                });

    }
*/
    //Todo Patch account

    public ResponseEntity<?> deleteAccount(Long id){
        return accountRepository.findById(id)
                .map(account -> {
                    accountRepository.delete(account);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new AccountNotFoundException(id));
    }

}
