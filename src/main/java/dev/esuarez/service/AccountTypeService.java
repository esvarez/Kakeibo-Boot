package dev.esuarez.service;

import dev.esuarez.error.accounttype.AccountTypeNotFoundException;
import dev.esuarez.model.AccountType;
import dev.esuarez.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    public List<AccountType> getAllAccountTypes(){
        return accountTypeRepository.findAll();
    }

    public AccountType findAccountTypeById(Long id){
        return accountTypeRepository.findById(id)
                .orElseThrow(() -> new AccountTypeNotFoundException(id));
    }

    public AccountType createAccountType(AccountType accountType){
        return accountTypeRepository.save(accountType);
    }

    public AccountType saveOrUpdate(AccountType accountType, Long id){
        return accountTypeRepository.findById(id)
                .map(accountType1 -> {
                    accountType.setName(accountType.getName());
                    accountType.setDescription(accountType.getDescription());

                    return accountTypeRepository.save(accountType);
                })
                .orElseGet(() -> {
                    accountType.setId(id);
                    return accountTypeRepository.save(accountType);
                });
    }

    // ToDo Patch Method

    public ResponseEntity<?> deleteAccountType(Long id){
        return accountTypeRepository.findById(id)
                .map(accountType -> {
                    accountTypeRepository.delete(accountType);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new AccountTypeNotFoundException(id));
    }
}
