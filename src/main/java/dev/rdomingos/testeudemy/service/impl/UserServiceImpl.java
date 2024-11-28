package dev.rdomingos.testeudemy.service.impl;

import dev.rdomingos.testeudemy.domain.User;
import dev.rdomingos.testeudemy.repository.UserRepository;
import dev.rdomingos.testeudemy.service.UserService;
import dev.rdomingos.testeudemy.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }
}
