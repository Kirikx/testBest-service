package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.Role;
import ru.testbest.dto.Userx;
import ru.testbest.persistence.dao.RoleDAO;
import ru.testbest.persistence.dao.UserxDAO;
import ru.testbest.persistence.mapper.UserxMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserxDAOImpl implements UserxDAO {

  @Autowired
  private RoleDAO roleDAO;

  JdbcTemplate jdbcTemplate;

  private final String SQL_FIND_USER = "select * from testdb.user where id = ?";
  private final String SQL_FIND_USERNAME = "select * from testdb.user where login = ?";
  private final String SQL_DELETE_USER = "delete from testdb.user where id = ?";
  private final String SQL_UPDATE_USER = "update testdb.user set login = ?, password = ?, active  = ? where id = ?";
  private final String SQL_GET_ALL = "select * from testdb.user";
  private final String SQL_INSERT_USER = "insert into testdb.user (login, password) values(?,?)";

  @Autowired
  public UserxDAOImpl(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Userx getUserById(Integer id) {
    try {
      Userx user = jdbcTemplate.queryForObject(SQL_FIND_USER, new Object[]{id}, new UserxMapper());
      List<Role> roles = roleDAO.getRolesByUserId(id);
        if (user != null) {
            user.setRoles(roles);
        }
      return user;
    } catch (EmptyResultDataAccessException ex) {
      return null;
    }
  }

  @Override
  public List<Userx> getAllUsers() {
    return jdbcTemplate.query(SQL_GET_ALL, new UserxMapper());
  }

  @Override
  public boolean deleteUser(Userx user) {
    return jdbcTemplate.update(SQL_DELETE_USER, user.getId()) > 0;
  }

  @Override
  public boolean updateUser(Userx user) {
    return
        jdbcTemplate.update(SQL_UPDATE_USER, user.getLogin(), user.getPassword(), user.isActive(),
            user.getId()) > 0;
  }

  @Override
  public boolean createUser(Userx user) {
    if (jdbcTemplate.update(SQL_INSERT_USER, user.getLogin(), user.getPassword()) > 0) {
      Userx userx = findByUsername(user.getLogin());
      List<Role> roles = user.getRoles();
        for (Role role : roles) {
            roleDAO.addGroupRoleToUserId(role.getName(), userx.getId());
        }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Userx findByUsername(String username) {
    try {
      Userx user = jdbcTemplate
          .queryForObject(SQL_FIND_USERNAME, new Object[]{username}, new UserxMapper());
      assert user != null;
      List<Role> roles = roleDAO.getRolesByUserId(user.getId());
      user.setRoles(roles);
      return user;
    } catch (EmptyResultDataAccessException ex) {
      return null;
    }

  }

}

