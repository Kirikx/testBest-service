package ru.testbest.dto.admin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserDto implements UserDetails {

  private String id;
  private String firstName;
  private String lastName;
  private String username;
  private char[] password;
  private char[] repeatPassword;
  private String phone;
  private String email;
  private Boolean isDeleted;
  private Set<RoleDto> roles;

  public String getPassword() {
    return Arrays.toString(password);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return isDeleted;
  }
}
