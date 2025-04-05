//package com.sparkplug.auth.presentation;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparkplug.auth.application.dto.request.*;
//import com.sparkplug.auth.application.dto.response.AuthResponse;
//import com.sparkplug.auth.application.usecase.*;
//import com.sparkplug.auth.infrastructure.security.service.JwtService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.http.MediaType;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AuthController.class)
//class AuthControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockitoBean
//    private LoginUseCase loginUseCase;
//    @MockitoBean
//    private ClientRegisterUseCase clientRegisterUseCase;
//    @MockitoBean
//    private AdminRegisterUseCase adminRegisterUseCase;
//    @MockitoBean
//    private UpdateCredentialsUseCase updateCredentialsUseCase;
//
//    @MockitoBean
//    private JwtService jwtService;
//
//    @BeforeEach
//    void setUp() {
//        Mockito.when(jwtService.generateToken(any())).thenReturn("mock-token");
//        Mockito.when(jwtService.validateToken(any(), any())).thenReturn(true);
//    }
//
//    private AuthResponse mockResponse = new AuthResponse(
//                1L,
//                "mockUsername",
//                "mockEmail",
//                "mockPhoneNumber",
//                "mock-token",
//                List.of());
//
//    @Test
//    void login_whenValidCredentials_thenReturnsToken() throws Exception {
//        var request = new LoginRequest("testUser", "password123");
//
//        Mockito.when(loginUseCase.login(any(LoginRequest.class))).thenReturn(mockResponse);
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("mock-token"));
//    }
//
//    @Test
//    void registerClientEmail_whenValidRequest_thenReturnsToken() throws Exception {
//        var request = new ClientRegisterEmailRequest("testUser", "email", "password123");
//
//        Mockito.when(clientRegisterUseCase.registerWithEmail(any(ClientRegisterEmailRequest.class)))
//                .thenReturn(mockResponse);
//
//        mockMvc.perform(post("/auth/register/email")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("mock-token"));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN_MANAGER"})
//    void registerAdminEmail_whenAuthorized_thenReturnsToken() throws Exception {
//        var request = new AdminRegisterEmailRequest(
//                "adminUser", "email@email.com", "password123", List.of("ADMIN_BASIC"));
//
//        Mockito.when(adminRegisterUseCase.registerWithEmail(any(AdminRegisterEmailRequest.class)))
//                .thenReturn(mockResponse);
//
//        mockMvc.perform(post("/auth/admins/register/email")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("mock-token"));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN_CLIENT"}) // Unauthorized role
//    void registerAdminEmail_whenUnauthorized_thenForbidden() throws Exception {
//        var request = new AdminRegisterEmailRequest(
//                "adminUser", "email@email.com", "password123", List.of("ADMIN_BASIC"));
//
//        mockMvc.perform(post("/auth/admins/register/email")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = {"CLIENT_BASIC"})
//    void updatePassword_whenAuthorized_thenSuccess() throws Exception {
//        var request = new UpdatePasswordRequest("oldPass123", "newPass456");
//
//        mockMvc.perform(put("/auth/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk());
//
//        Mockito.verify(updateCredentialsUseCase).updatePassword(any(Long.class), any(UpdatePasswordRequest.class));
//    }
//}
