package dev.esuarez.controller;

import dev.esuarez.model.Account;
import dev.esuarez.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static dev.esuarez.config.KakeiboUri.*;

@RestController
@RequestMapping(API)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(ACCOUNT_API)
    List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @GetMapping(ACCOUNT_API + "/{id}")
    Account getAccount(@PathVariable @Min(1) Long id){
        return accountService.findAccountById(id);
    }

    @GetMapping(USERS_API + "/{userId}" + ACCOUNT_API)
    List<Account> getAllAccountsByUserId(@PathVariable (value = "userId") @Min(1) Long userId){
        return accountService.getAllAccountsByUserId(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(USERS_API + "/{userId}" + ACCOUNT_API)
    Account createAccount(@PathVariable (value = "userId") Long userId,
                          @Valid @RequestBody Account account){
        return accountService.createAccount(userId, account);
    }

    @PutMapping(USERS_API + "/{userId}" + ACCOUNT_API)
    Account saveOrUpdateAccunt (@PathVariable (value = "userId") Long userId,
                          @Valid @RequestBody Account account){
        return accountService.createAccount(userId, account);
    }


}
