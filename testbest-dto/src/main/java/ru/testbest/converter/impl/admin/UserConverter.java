package ru.testbest.converter.impl.admin;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.persistence.entity.User;

@Component
@RequiredArgsConstructor
public class UserConverter implements ConverterTest<User, UserDto> {

    private static final RoleConverter roleConverter = new RoleConverter();

    @Override
    public UserDto convertToDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setIsDeleted(entity.getIsDeleted());
        userDto.setRoles(entity.getRoles().stream()
                .map(roleConverter::convertToDto)
                .collect(Collectors.toSet()));
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto dto) {
        User user = new User();
        if (dto.getId() != null) user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setIsDeleted(dto.getIsDeleted());
        user.setRoles(dto.getRoles().stream()
                .map(roleConverter::convertToEntity)
                .collect(Collectors.toSet()));
        return user;
    }
}
