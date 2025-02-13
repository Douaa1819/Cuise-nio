package com.youcode.cuisenio.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youcode.cuisenio.features.auth.dto.request.LoginRequest;
import com.youcode.cuisenio.features.auth.dto.request.RegisterRequest;
import com.youcode.cuisenio.features.auth.dto.response.LoginResponse;
import com.youcode.cuisenio.features.auth.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void registerSuccess() throws Exception {
//        RegisterRequest = new RegisterRequest(
//                "testuser",
//                "Test",
//                "test@example.com",
//                "Test123@password",
//                Role.USER
//        );
//
//        mockMvc.perform(post("/v1/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.username").value("testuser"))
//                .andExpect(jsonPath("$.email").value("test@example.com"));
//    }
//
//    @Test
//    void loginSuccess() throws Exception {
//        // D'abord enregistrer un utilisateur
//        RegisterRequest registerRequest = new RegisterRequest(
//                "logintest",
//                "Test",
//                "login@example.com",
//                "Test123@password",
//                Role.USER
//        );
//        mockMvc.perform(post("/v1/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isCreated());
//
//        // Ensuite tester le login
//        LoginRequest loginRequest = new LoginRequest(
//                "login@example.com",
//                "Test123@password"
//        );
//        mockMvc.perform(post("/v1/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").exists());
//    }
//
//    @Test
//    void testProtectedEndpoint() throws Exception {
//        // Test d'un endpoint protégé sans token
//        mockMvc.perform(get("/api/protected"))
//                .andExpect(status().isUnauthorized());
//
//        // Test avec un token valide
//        String token = getValidToken();
//        mockMvc.perform(get("/api/protected")
//                        .header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk());
//    }
//
//    private String getValidToken() throws Exception {
//        // Code pour obtenir un token valide via login
//        LoginRequest loginRequest = new LoginRequest("login@example.com", "Test123@password");
//        MvcResult result = mockMvc.perform(post("/v1/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andReturn();
//
//        LoginResponse response = objectMapper.readValue(
//                result.getResponse().getContentAsString(),
//                LoginResponse.class
//        );
//        return response.token();
//    }
}