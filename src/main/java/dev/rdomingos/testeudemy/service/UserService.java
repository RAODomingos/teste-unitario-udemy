package dev.rdomingos.testeudemy.service;

import dev.rdomingos.testeudemy.domain.User;
import dev.rdomingos.testeudemy.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDTO user);

    User update(UserDTO user);
    void delete(Integer id);
 }
