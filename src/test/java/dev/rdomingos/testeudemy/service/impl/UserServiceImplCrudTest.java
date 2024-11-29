package dev.rdomingos.testeudemy.service.impl;

import dev.rdomingos.testeudemy.domain.User;
import dev.rdomingos.testeudemy.domain.dto.UserDTO;
import dev.rdomingos.testeudemy.repository.UserRepository;
import dev.rdomingos.testeudemy.service.exception.DataIntegratyViolationExcepiton;
import dev.rdomingos.testeudemy.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplCrudTest  {

    public static final int ID = 1;
    public static final String NAME = "Rafael Domingos";
    public static final String MAIL = "Q2V0W@example.com";
    public static final String PASSWORD = "123456";
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();

    }

    @Test
    @DisplayName("whenFindByIdThenReturnAnUserInstance")
    void findById() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());

    }

    @Test
    @DisplayName("whenFindByIdThenReturnAnException")
    void findByIdNotFound() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto nao encontrado"));

        try{
            service.findById(ID);
        }catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Objeto nao encontrado", e.getMessage());
        }
    }


    @Test
    @DisplayName("whenFindAllThenReturnAListOfUsers")
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(user, user));
        List<User> response = service.findAll();
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(User.class, response.get(0).getClass());

        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(MAIL, response.get(0).getEmail());
    }

    @Test
    @DisplayName("whenCreateThenReturnSucess")
    void create() {
        when(repository.save(any())).thenReturn(user);
        User response = service.create(userDTO);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    @DisplayName("whenCreateThenReturnAnDataIntegratyViolationExcepiton")
    void createDataException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        }catch (Exception e){
            assertEquals(DataIntegratyViolationExcepiton.class, e.getClass());
            assertEquals("Email já cadastrado no sistema", e.getMessage());
        }

    }

    @Test
    @DisplayName("whenUpdateThenReturnSucess")
    void update() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(MAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    @DisplayName("whenUpdateThenReturnAnDataIntegratyViolationExcepiton")
    void updateDataException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.update(userDTO);
        }catch (Exception e){
            assertEquals(DataIntegratyViolationExcepiton.class, e.getClass());
            assertEquals("Email já cadastrado no sistema", e.getMessage());
        }

    }

    @Test
    @DisplayName("whenDeleteThenReturnSucess")
    void delete() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());

    }

    @Test
    @DisplayName("whenDeleteThenReturnAnObjectNotFoundException")
    void deleteObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto nao encontrado"));
        try{
            service.delete(ID);
        }catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Objeto nao encontrado", e.getMessage());
        }
    }


    private void startUser(){
        user = new User(ID, NAME, MAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, MAIL, PASSWORD));
    }

}