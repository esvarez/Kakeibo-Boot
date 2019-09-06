package dev.esuarez.config;

import dev.esuarez.model.Account;
import dev.esuarez.model.AccountType;
import dev.esuarez.model.User;
import dev.esuarez.service.AccountService;
import dev.esuarez.service.AccountTypeService;
import dev.esuarez.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configdb {

    @Bean
    public CommandLineRunner unitDataBase(UserService userService, AccountTypeService accountTypeService,
                                          AccountService accountService){
        return args -> {
            System.out.println("Starting Database...");
            User user = User.builder().id(1L).email("email@mail.com").password("Password").user("user").build();
            AccountType accountType = AccountType.builder().id(1L).name("savings").build();
            Account account = Account.builder().name("Budget").user(user).accountType(accountType).build();

            userService.createUser(user);
            accountTypeService.createAccountType(accountType);
            accountService.createAccount(user.getId(), account);

            System.out.println("... Saved");
        };

    }
}
