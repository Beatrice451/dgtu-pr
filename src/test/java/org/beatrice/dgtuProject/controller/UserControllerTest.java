package org.beatrice.dgtuProject.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.beatrice.dgtuProject.dto.UserRequest;
import org.beatrice.dgtuProject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser_shouldReturn201() throws JsonProcessingException {
        UserRequest request = new UserRequest("Beatrice", "test@example.com", "Moscow", "password");
        String jsonRequest = new ObjectMapper().writeValueAsString(request);

        doNothing().when(userService).registerUser(any(UserRequest.class));
    }
}
