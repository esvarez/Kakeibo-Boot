package dev.esuarez.controller;

import dev.esuarez.model.AccountType;
import dev.esuarez.service.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

import static dev.esuarez.config.KakeiboUri.*;

@RestController
@RequestMapping(API)
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping(ACCOUNT_TYPE_API)
    List<AccountType> getAllAccountTypes() {
        return accountTypeService.getAllAccountTypes();
    }

    @GetMapping(ACCOUNT_TYPE_API + "/{id}")
    AccountType getAccountType(@PathVariable @Min(1) Long id){
        return accountTypeService.findAccountTypeById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(ACCOUNT_TYPE_API)
    AccountType createAccountType(@Valid @RequestBody AccountType accountType) {
        return accountTypeService.createAccountType(accountType);
    }

    @PutMapping(ACCOUNT_TYPE_API + "/{id}")
    AccountType saveOrUpdateAccountType(@Valid @RequestBody AccountType accountType, @PathVariable @Min(1) Long id){
        return accountTypeService.saveOrUpdate(accountType, id);
    }

    //ToDo Patch Method

    @DeleteMapping(ACCOUNT_TYPE_API + "/{id}")
    ResponseEntity<?> saveOrUpdate(@PathVariable @Min(1) Long id){
        return accountTypeService.deleteAccountType(id);
    }

}
