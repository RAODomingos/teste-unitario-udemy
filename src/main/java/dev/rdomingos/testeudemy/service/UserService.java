package dev.rdomingos.testeudemy.service;

import dev.rdomingos.testeudemy.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();
 }
