package dev.rdomingos.testeudemy.service.impl;

import dev.rdomingos.testeudemy.domain.User;
import dev.rdomingos.testeudemy.domain.dto.UserDTO;
import dev.rdomingos.testeudemy.repository.UserRepository;
import dev.rdomingos.testeudemy.service.UserService;
import dev.rdomingos.testeudemy.service.exception.DataIntegratyViolationExcepiton;
import dev.rdomingos.testeudemy.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(UserDTO user) {
        findByEmail(user);
        return repository.save(mapper.map(user, User.class));
    }

    private void findByEmail(UserDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if(user.isPresent()) {
            throw new DataIntegratyViolationExcepiton("Email já cadastrado no sistema");
        }
    }

}
