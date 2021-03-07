package ru.testbest.persistence.dao.service;

import ru.testbest.dto.Role;
import ru.testbest.dto.Userx;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.testbest.persistence.dao.UserxDAO;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserxDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userDAO.findByUsername(userName);
    }

    public boolean addUser(Userx user) {
        Userx userFromDB = userDAO.findByUsername(user.getLogin());
        Role role = new Role();

        if (userFromDB != null) {
            return false;
        }

        role.setName("ROLE_USER");
        List<Role> roles  = new ArrayList();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.createUser(user);

        return true;
    }
}