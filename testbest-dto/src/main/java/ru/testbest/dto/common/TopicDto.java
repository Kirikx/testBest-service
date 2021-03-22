package ru.testbest.dto.common;

import lombok.Data;

import java.util.UUID;

@Data
public class TopicDto {

  private UUID id;
  private String name;
  private String description;
}
