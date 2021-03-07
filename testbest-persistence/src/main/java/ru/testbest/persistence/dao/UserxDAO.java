package ru.testbest.persistence.dao;

import ru.testbest.dto.Userx;
import java.util.List;

public interface UserxDAO {

    Userx getUserById(Integer id);

    List<Userx> getAllUsers();

    boolean deleteUser(Userx user);

    boolean updateUser(Userx user);

    boolean createUser(Userx user);

    Userx findByUsername(String username);
}
