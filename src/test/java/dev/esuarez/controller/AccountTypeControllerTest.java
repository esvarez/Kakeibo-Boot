package dev.esuarez.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.esuarez.model.Account;
import dev.esuarez.model.AccountType;
import dev.esuarez.service.AccountTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class AccountTypeControllerTest {

    private static final ObjectMapper om =  new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTypeService mockAccountType;

    @Before
    public void setUp(){
        AccountType accountType = AccountType.builder()
                .id(1L).name("Ahorros").description("Account to save money")
                .build();
        when(mockAccountType.findAccountTypeById(1L)).thenReturn(accountType);
    }

    @Test
    public void find_allAccountType_OK() throws Exception {
        List<AccountType> accountTypes = Arrays.asList(
                AccountType.builder().id(3L).name("Credit cards").build(),
                AccountType.builder().id(2L).name("Found Save").description("Some description").build()
        );
        when(mockAccountType.getAllAccountTypes()).thenReturn(accountTypes);

        mockMvc.perform(get("/api/account-types"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("Credit cards")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Found Save")))
                .andExpect(jsonPath("$[1].description", is("Some description")));

        verify(mockAccountType, times(1)).getAllAccountTypes();
    }

    @Test
    public void find_accountTypeById_OK() throws Exception{
        mockMvc.perform(get("/api/account-types/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Ahorros")))
                .andExpect(jsonPath("$.description", is("Account to save money")));
        verify(mockAccountType, times(1)).findAccountTypeById(1L);
    }

    @Test
    public void save_invalidAccountType_400() throws Exception {
        AccountType accountType = AccountType.builder().id(1L).description("Description x").build();

        mockMvc.perform(post("/api/account-types")
                .content(om.writeValueAsString(accountType))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a name")));

    }

    @Test
    public void save_accountType_OK() throws Exception{
        AccountType accountType = AccountType.builder().id(7L)
                .name("Money")
                .description("Account to track my spends").build();

        when(mockAccountType.createAccountType(any(AccountType.class))).thenReturn(accountType);

        mockMvc.perform(post("/api/account-types")
                .content(om.writeValueAsString(accountType))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.name", is("Money")))
                .andExpect(jsonPath("$.description", is("Account to track my spends")));
    }

    @Test
    public void update_accountType_OK() throws Exception {
        AccountType accountType = AccountType.builder().id(1L).name("Name updated").build();

        when(mockAccountType.saveOrUpdate(any(AccountType.class), any(Long.class))).thenReturn(accountType);

        mockMvc.perform(put("/api/account-types/1")
                .content(om.writeValueAsString(accountType))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Name updated")));
    }

    @Test
    public void delete_accountType_OK() throws Exception{

        //ResponseEntity<?> response = new ResponseEntity.

        mockMvc.perform(delete("/api/account-types/1"))
                .andExpect(status().isOk());

    }

}
