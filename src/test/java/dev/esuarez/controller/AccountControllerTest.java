package dev.esuarez.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.esuarez.model.Account;
import dev.esuarez.model.AccountType;
import dev.esuarez.model.User;
import dev.esuarez.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService mockAccountService;

    @Before
    public void setUp(){
        User user = User.builder().id(1L).email("email@mail.com").password("Password").user("user").build();
        AccountType accountType = AccountType.builder().id(1L).name("savings").build();
        Account account = Account.builder().id(1L).name("Budget").user(user).accountType(accountType).build();

        when(mockAccountService.findAccountById(1L)).thenReturn(account);
    }

    @Test
    public void find_accounts_OK() throws Exception{
        AccountType accountType = AccountType.builder().name("Savings").build();
        List<Account> accounts = Arrays.asList(
                Account.builder().name("Budget").accountType(accountType).build(),
                Account.builder().name("Account Type").accountType(accountType).build()
        );
        when(mockAccountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(mockAccountService, times(1)).getAllAccounts();
    }

    @Test
    public void find_accountById_OK() throws Exception{

    }

}
