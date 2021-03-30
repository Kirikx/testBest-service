package ru.testbest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@ActiveProfiles("test")
class AppTests {

	@Test
	void contextLoads() {
	}

	@Test
  public void dummyTest() {
    System.out.println("Hello world!");
  }

}
