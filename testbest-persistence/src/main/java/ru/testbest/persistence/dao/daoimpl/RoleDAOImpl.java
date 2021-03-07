package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.Role;
import ru.testbest.persistence.dao.RoleDAO;
import ru.testbest.persistence.mapper.RoleMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDAOImpl implements RoleDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ROLE = "select * from testdb.role where id = ?";
    private final String SQL_DELETE_ROLE = "delete from testdb.role where id = ?";
    private final String SQL_UPDATE_ROLE = "update testdb.role set name = ? where id = ?";
    private final String SQL_GET_ALL = "select * from testdb.role";
    private final String SQL_INSERT_ROLE = "insert into testdb.role (name) values(?)";
    private final String SQL_FIND_ROLES_TO_USER_ID = "select r.id, r.name from testdb.group_role g left join testdb.role r on g.role_id = r.id where g.user_id = ?";
    private final String SQL_ADD_USER_ROLE_TO_GROUP_ROLE = "insert into testdb.group_role (role_id, user_id) values(?,?)";
    private final String SQL_FIND_ROLE_BY_NAME = "select * from testdb.role where name = ?";

    @Autowired
    public RoleDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Role getRoleById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_ROLE, new Object[]{id}, new RoleMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Role> getRolesByUserId(Integer id) {
        return jdbcTemplate.query(SQL_FIND_ROLES_TO_USER_ID, new Object[]{id}, new RoleMapper());
    }

    @Override
    public List<Role> getAllRoles() {
        return jdbcTemplate.query(SQL_GET_ALL, new RoleMapper());
    }

    @Override
    public boolean deleteRole(Role role) {
        return jdbcTemplate.update(SQL_DELETE_ROLE, role.getId()) > 0;
    }

    @Override
    public boolean updateRole(Role role) {
        return jdbcTemplate.update(SQL_UPDATE_ROLE, role.getName(), role.getId()) > 0;
    }

    @Override
    public boolean createRole(Role role) {
        return jdbcTemplate.update(SQL_INSERT_ROLE, role.getName(), role.getId()) > 0;
    }

    @Override
    public boolean addGroupRoleToUserId(String role_name, Integer user_id) {
        Role role = getRoleIdByName(role_name);
        if (role != null) {
            return jdbcTemplate.update(SQL_ADD_USER_ROLE_TO_GROUP_ROLE, role.getId(), user_id) > 0;
        } else {
            return false;
        }
    }

    @Override
    public Role getRoleIdByName(String role_name) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_ROLE_BY_NAME, new Object[]{role_name}, new RoleMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}