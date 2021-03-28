package ru.testbest.conf;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
      .setMatchingStrategy(MatchingStrategies.STRICT)
      .setFieldMatchingEnabled(true)
      .setSkipNullEnabled(true)
      .setFieldAccessLevel(PRIVATE);
    return mapper;
  }

}
