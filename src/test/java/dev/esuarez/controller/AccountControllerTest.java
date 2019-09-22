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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static dev.esuarez.config.KakeiboUri.*;
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
                Account.builder().id(2L).name("Budget").accountType(accountType).build(),
                Account.builder().id(4L).name("Account Type").accountType(accountType).build()
        );
        when(mockAccountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get(API + ACCOUNT_API))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Budget")))
                .andExpect(jsonPath("$[1].id", is(4)))
                .andExpect(jsonPath("$[1].name", is("Account Type")));

        verify(mockAccountService, times(1)).getAllAccounts();
    }

    @Test
    public void find_accountById_OK() throws Exception{
        mockMvc.perform(get(API + ACCOUNT_API + "/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Budget")));

        verify(mockAccountService, times(1)).findAccountById(1L);
    }

    @Test
    public void save_account_OK() throws Exception {
        User user = User.builder().id(1L).email("email@mail.com").password("Password").user("user").build();
        AccountType accountType = AccountType.builder().id(1L).name("Savings").build();
        Account account = Account.builder().id(1L).name("Budget").user(user).accountType(accountType).build();

        when(mockAccountService.createAccount(user.getId(), account)).thenReturn(account);

        mockMvc.perform(post(API + USERS_API + "/1" + ACCOUNT_API)
                .content(om.writeValueAsString(account))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

        verify(mockAccountService, times(1)).createAccount(any(Long.class), any(Account.class));

    }

}
