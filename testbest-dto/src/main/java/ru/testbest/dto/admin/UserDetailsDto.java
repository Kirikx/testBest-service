package ru.testbest.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.testbest.dto.BaseDTO;

@Data
public class UserDetailsDto implements BaseDTO, UserDetails {

  private UUID id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String repeatPassword;
  private String phone;
  private String email;
  private Boolean isDeleted;
  private Set<RoleDto> roles;


  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !isDeleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailsDto user = (UserDetailsDto) o;
    return Objects.equals(id, user.id);
  }
}
