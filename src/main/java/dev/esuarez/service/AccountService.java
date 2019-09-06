package dev.esuarez.service;

import dev.esuarez.error.account.AccountNotFoundException;
import dev.esuarez.error.accounttype.AccounTypeNotFoundException;
import dev.esuarez.error.user.UserNotFoundException;
import dev.esuarez.model.Account;
import dev.esuarez.repository.AccountRepository;
import dev.esuarez.repository.AccountTypeRepository;
import dev.esuarez.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //System.out.println(account.toString());
        Long accountTypeId = account.getAccountType().getId();
        return userRepository.findById(userId)
                .map(user -> {
                    return accountTypeRepository.findById(accountTypeId)
                            .map(accountType -> {
                                account.setUser(user);
                                account.setAccountType(accountType);
                                return accountRepository.save(account);
                            }).orElseThrow(() -> new AccounTypeNotFoundException(accountTypeId));
                }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Account updateAccount(Long userId, Long accountId, Account account){
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }

        return accountRepository.findById(accountId)
                .map( account1 -> {
                    account.setName(account.getName());
                    account.setDescription(account.getDescription());
                    return accountRepository.save(account);
                }).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

}
