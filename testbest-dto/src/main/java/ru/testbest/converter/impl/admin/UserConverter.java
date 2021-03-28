package ru.testbest.converter.impl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.persistence.entity.User;

@Component
public class UserConverter extends AbstractMapper<User, UserDto> {

  @Autowired
  public UserConverter() {
    super(User.class, UserDto.class);
  }
}
