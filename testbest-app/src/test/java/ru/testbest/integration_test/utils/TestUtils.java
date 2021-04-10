package ru.testbest.integration_test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import ru.testbest.dto.admin.security.response.Jwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestUtils {

  public static String authenticate(MockMvc mockMvc,
                                    ObjectMapper objectMapper,
                                    String username,
                                    String password) throws Exception {
    String responce = mockMvc.perform(
      post("/api/auth/signin")
        .contentType("application/json;charset=UTF-8")
        .content("{\"password\":\"" + password + "\",\"username\":\"" + username + "\"}")
    ).andReturn().getResponse().getContentAsString();

    return objectMapper.readValue(responce, Jwt.class).getAccessToken();
  }
}
