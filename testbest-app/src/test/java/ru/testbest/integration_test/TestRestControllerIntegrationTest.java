package ru.testbest.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.testbest.SpringBootSecurityJwtApplication;
import ru.testbest.converter.impl.manage.TestFullConverter;
import ru.testbest.converter.impl.test.TestConverter;
import ru.testbest.dto.manage.TestFullDto;
import ru.testbest.dto.test.TestDto;
import ru.testbest.integration_test.utils.TestUtils;
import ru.testbest.persistence.dao.TestDao;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringBootSecurityJwtApplication.class)
@ActiveProfiles("test")
public class TestRestControllerIntegrationTest {

  private static final String URI = "/api/tests";

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private FilterChainProxy springSecurityFilterChain;

  @Autowired
  private TestDao testRepository;

  @Autowired
  TestConverter testConverter;

  @Autowired
  TestFullConverter testFullConverter;

  @Autowired
  ObjectMapper objectMapper;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
      .addFilter(springSecurityFilterChain).build();
  }

  @Test
  @DisplayName("Tesing TestRestController GET by id")
  public void whenStoredTestIsRequested_itIsCorrect() throws Exception {

    String testId = "dec15719-95f2-46a1-931a-865617972d70";
    TestDto expected = testConverter.convertToDto(
      testRepository.findById(UUID.fromString(testId)).get());
    String expectedJSON = objectMapper.writeValueAsString(expected);

    MvcResult res = mockMvc.perform(get(URI + "/" + testId))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(content().json(expectedJSON))
      .andReturn();

    TestDto actual = objectMapper.readValue(
        res.getResponse().getContentAsString(StandardCharsets.UTF_8),
        TestDto.class);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Tesing TestRestController GET")
  public void whenAllTestsAreRequested_itIsCorrect() throws Exception {

    List<TestDto> expected = testRepository.findAll().stream()
      .map(it -> testConverter.convertToDto(it))
      .collect(Collectors.toList());
    String expectedJSON = objectMapper.writeValueAsString(expected);

    MvcResult res = mockMvc.perform(get(URI))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(content().json(expectedJSON))
      .andReturn();

    List<TestDto> actual = Arrays.asList(objectMapper.readValue(
      res.getResponse().getContentAsString(StandardCharsets.UTF_8),
      TestDto[].class));

    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Create / Delete test")
  public void whenCreateTest_Success() throws Exception {

    String createTestJSON = new String(
      Files.readAllBytes(
        new ClassPathResource("testDTO/create-test.json")
          .getFile()
          .toPath()));

    MvcResult res = mockMvc.perform(
      post(URI + "/create")
        .header("Authorization", "Bearer " +
          TestUtils.authenticate(mockMvc, objectMapper, "manager", "guest"))
        .contentType("application/json;charset=UTF-8")
        .content(createTestJSON)
        .accept("application/json;charset=UTF-8")
    )
      .andExpect(status().isOk())
      .andReturn();

    TestFullDto actual = objectMapper.readValue(
      res.getResponse().getContentAsString(StandardCharsets.UTF_8),
      TestFullDto.class);

    TestFullDto expected = testFullConverter.convertToDto(
      testRepository.findById(UUID.fromString(actual.getId().toString())).get());

    Assertions.assertEquals(expected, actual);

    mockMvc.perform(delete((URI + "/" + actual.getId().toString())))
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Update test")
  public void whenUpdateTest_itIsCorrect() throws Exception {

    String testId = "dec15719-95f2-46a1-931a-865617972d70";
    TestFullDto expected = testFullConverter.convertToDto(
      testRepository.findById(UUID.fromString(testId)).get());
    expected.setName("Краказябра");

    mockMvc.perform(
      put(URI).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(expected)))
      .andExpect(status().isOk())
      .andReturn();

    TestFullDto actual = testFullConverter.convertToDto(
      testRepository.findById(UUID.fromString(testId)).get());

    Assertions.assertEquals(expected.getName(), actual.getName());
  }
}
