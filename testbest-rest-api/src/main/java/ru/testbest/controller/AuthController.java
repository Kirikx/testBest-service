package ru.testbest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.admin.RoleDto;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.dto.admin.security.request.Auth;
import ru.testbest.dto.admin.security.response.Jwt;
import ru.testbest.dto.MessageResponse;
import ru.testbest.service.impl.security.UserDetailsImpl;
import ru.testbest.service.impl.admin.RoleServiceImpl;
import ru.testbest.service.impl.admin.UserServiceImpl;
import ru.testbest.service.impl.security.jwt.JwtUtils;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserServiceImpl userService;
  private final RoleServiceImpl roleService;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody Auth auth) {

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    return ResponseEntity.ok(new Jwt(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
    if (userService.existNameUser(userDto.getUsername())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userService.existEmailUser(userDto.getEmail())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Email is already in use!"));
    }

    Set<RoleDto> strRoles = userDto.getRoles();
    Set<RoleDto> roles = new HashSet<>();

    if (strRoles == null) {
      RoleDto userRole = roleService.getRoleByName("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role.getName()) {
          case "admin":
            RoleDto adminRole = roleService.getRoleByName("ROLE_ADMIN")
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          case "manager":
            RoleDto modRole = roleService.getRoleByName("ROLE_MANAGER")
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          default:
            RoleDto userRole = roleService.getRoleByName("ROLE_USER")
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    userDto.setPassword(encoder.encode(userDto.getPassword()));
    userDto.setIsDeleted(false);
    userDto.setRoles(roles);
    userService.createUser(userDto);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
