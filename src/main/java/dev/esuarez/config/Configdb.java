package dev.esuarez.config;

import dev.esuarez.model.*;
import dev.esuarez.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Configdb {

    @Bean
    public CommandLineRunner unitDataBase(UserService userService, AccountTypeService accountTypeService,
                                          AccountService accountService, MovementCategoryService movementCategoryService,
                                          RollService rollService){
        return args -> {
            System.out.println("Starting Database...");


/*
            User user = User.builder().id(1L).email("email@mail.com").password("Password").user("user").build();
            userService.createUser(user);

            AccountType accountType = AccountType.builder().id(1L).name("savings").build();
            accountTypeService.createAccountType(accountType);

            MovementCategory movementCategory = MovementCategory.builder()
                    .id(1L).name("Food")
                    .category("Expense")
                    .image("Valid url")
                    .user(user).build();
            movementCategoryService.createMovementCategory(user.getId(), movementCategory);


            Account account = Account.builder().id(1L).name("Budget").user(user).accountType(accountType).build();
            Account account2 = Account.builder().id(2L).name("Credit Bank").user(user).accountType(accountType).build();
            accountService.createAccount(user.getId(), account);
            accountService.createAccount(user.getId(), account2);
*/
            System.out.println("... Saved");
        };

    }
}
