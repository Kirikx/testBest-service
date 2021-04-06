package ru.testbest.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.MessageResponse;
import ru.testbest.dto.admin.RoleDto;
import ru.testbest.dto.admin.UserDetailsDto;
import ru.testbest.dto.admin.security.request.Auth;
import ru.testbest.dto.admin.security.response.Jwt;
import ru.testbest.exception.custom.CustomNotFoundException;
import ru.testbest.service.impl.admin.RoleServiceImpl;
import ru.testbest.service.impl.admin.UserServiceImpl;
import ru.testbest.service.impl.admin.jwt.JwtUtils;

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

      UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
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
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDetailsDto userDetailsDto) {
    if (userService.existNameUser(userDetailsDto.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userService.existEmailUser(userDetailsDto.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    Set<RoleDto> strRoles = userDetailsDto.getRoles();
    Set<RoleDto> roles = new HashSet<>();

        if (strRoles == null) {
          RoleDto userRole = roleService.getRoleByName("ROLE_USER")
              .orElseThrow(() -> new CustomNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.getName()) {
                    case "admin":
                      RoleDto adminRole = roleService.getRoleByName("ROLE_ADMIN")
                          .orElseThrow(
                              () -> new CustomNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                      RoleDto modRole = roleService.getRoleByName("ROLE_MANAGER")
                          .orElseThrow(
                              () -> new CustomNotFoundException("Error: Role is not found."));
                      roles.add(modRole);
                      break;
                  default:
                    RoleDto userRole = roleService.getRoleByName("ROLE_USER")
                        .orElseThrow(
                            () -> new CustomNotFoundException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

    userDetailsDto.setPassword(encoder.encode(userDetailsDto.getPassword()));
    userDetailsDto.setIsDeleted(false);
    userDetailsDto.setRoles(roles);
    userService.createUser(userDetailsDto);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
