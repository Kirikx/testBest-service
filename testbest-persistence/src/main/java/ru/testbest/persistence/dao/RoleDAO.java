package ru.testbest.persistence.dao;

import ru.testbest.dto.Role;
import java.util.List;

public interface RoleDAO {

    Role getRoleById(Integer id);

    List<Role> getAllRoles();

    boolean deleteRole(Role role);

    boolean updateRole(Role role);

    boolean createRole(Role role);

    List<Role> getRolesByUserId(Integer id);

    boolean addGroupRoleToUserId(String role_name, Integer user_id);

    Role getRoleIdByName(String role_name);

}
