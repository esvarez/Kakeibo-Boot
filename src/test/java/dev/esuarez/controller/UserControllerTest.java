package dev.esuarez.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.esuarez.model.User;
import dev.esuarez.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class UserControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Before
    public void setUp(){
        User user = User.builder().id(1L).email("email@com.mx").password("encrypt").build();
        when(mockUserService.findUserById(1L)).thenReturn(user);
    }

    @Test
    public void find_allUsers_OK() throws Exception {
        List<User> users = Arrays.asList(
                User.builder().id(1L).user("Erick").email("at@mail.com").password("password").build(),
                User.builder().id(2L).user("Suarez").email("suarez@gmail.com").password("1234pass").build());
        when(mockUserService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].user",is("Erick")))
                .andExpect(jsonPath("$[0].password",is("password")))
                .andExpect(jsonPath("$[0].email", is("at@mail.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].user",is("Suarez")))
                .andExpect(jsonPath("$[1].password",is("1234pass")))
                .andExpect(jsonPath("$[1].email", is("suarez@gmail.com")));

        verify(mockUserService, times(1)).getAllUsers();
    }

    @Test
    public void find_userById_OK() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.user", is(nullValue())))
                .andExpect(jsonPath("$.password", is("encrypt")))
                .andExpect(jsonPath("$.email", is("email@com.mx")));

        verify(mockUserService, times(1)).findUserById(1L);
    }

    @Test
    public void find_userNotFound_404() throws Exception {
        mockMvc.perform(get("/api/users/404")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void save_invalidUser_email_400() throws Exception {
        String userJson = "{\"user\":\"miniuser\",\"email\":\"emailinvalid\"}";

        mockMvc.perform(post("/api/users")
                .content(userJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("You should provide a password.")))
                .andExpect(jsonPath("$.errors", hasItem("Email must be a valid email address.")));
    }

    @Test
    public void save_user_OK() throws Exception {
        User newUser = User.builder().id(2L).user("Suarez").email("suarez@gmail.com").password("1234pass").build();

        when(mockUserService.createUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/api/users")
                .content(om.writeValueAsString(newUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.user", is("Suarez")))
                .andExpect(jsonPath("$.password", is("1234pass")))
                .andExpect(jsonPath("$.email", is("suarez@gmail.com")));

        verify(mockUserService, times(1)).createUser(any(User.class));
    }

    @Test
    public void update_user_OK() throws Exception {

        User updateUser = User.builder().id(1L).user("Erick").email("at@mail.com").password("password").build();

        when(mockUserService.saveOrUpdateUser(any(User.class), any(Long.class))).thenReturn(updateUser);

        mockMvc.perform(put("/api/users/1")
                .content(om.writeValueAsString(updateUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.user", is("Erick")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.email", is("at@mail.com")));
    }

    @Test
    public void patch_user_OK() throws Exception {
        when(mockUserService.patchUser(any(Map.class), any(Long.class))).thenReturn(new User());
        String patchInJson = "{\"user\":\"new user\"}";

        mockMvc.perform(patch("/api/users/2")
                .content(patchInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void patch_user_405() throws Exception {

        String patchInJson = "{\"email\":\"myemail\"}";

        mockMvc.perform(patch("/api/users/2")
                .content(patchInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //verify(mockUserService, times(0)).patch()
    }

    @Test
    public void delete_user_OK() throws Exception {

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_userNotFound() throws Exception {
        mockMvc.perform(delete("/api/users/404"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
