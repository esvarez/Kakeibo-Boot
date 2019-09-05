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

@RestController
@RequestMapping("/api")
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping("/account-types")
    List<AccountType> getAllAccountTypes() {
        return accountTypeService.getAllAccountTypes();
    }

    @GetMapping("/account-types/{id}")
    AccountType getAccountType(@PathVariable @Min(1) Long id){
        return accountTypeService.findAccountTypeById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/account-types")
    AccountType createAccountType(@Valid @RequestBody AccountType accountType) {
        return accountTypeService.createAccountType(accountType);
    }

    @PutMapping("/account-types/{id}")
    AccountType saveOrUpdateAccountType(@Valid @RequestBody AccountType accountType, @PathVariable @Min(1) Long id){
        return accountTypeService.saveOrUpdate(accountType, id);
    }

    //ToDo Patch Method

    @DeleteMapping("/account-types/{id}")
    ResponseEntity<?> saveOrUpdate(@PathVariable @Min(1) Long id){
        return accountTypeService.deleteAccountType(id);
    }

}
