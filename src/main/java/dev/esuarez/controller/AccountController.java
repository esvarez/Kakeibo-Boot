package dev.esuarez.controller;

import dev.esuarez.model.Account;
import dev.esuarez.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    Account getAccount(@PathVariable @Min(1) Long id){
        return accountService.findAccountById(id);
    }

    @GetMapping("/users/{userId}/accounts")
    List<Account> getAllAccountsByUserId(@PathVariable (value = "userId") @Min(1) Long userId){
        //return accountService.getAllAccountsByUserId(userId);
        return accountService.getAllAccounts();
    }

    @PostMapping("/users/{userId}/accounts")
    Account createAccount(@PathVariable (value = "userId") Long userId,
                          @Valid @RequestBody Account account){
        return accountService.createAccount(userId, account);
    }

}
