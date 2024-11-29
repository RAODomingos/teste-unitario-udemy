package dev.rdomingos.testeudemy.resource;

import dev.rdomingos.testeudemy.domain.User;
import dev.rdomingos.testeudemy.domain.dto.UserDTO;
import dev.rdomingos.testeudemy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    public static final int ID = 1;
    public static final String NAME = "Rafael Domingos";
    public static final String MAIL = "Q2V0W@example.com";
    public static final String PASSWORD = "123456";

    private User user;
    private UserDTO userDTO;



    @InjectMocks
    private UserResource resource;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    @DisplayName("whenFindByIdThenReturnSuccess")
    void findById() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(MAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());

    }

    @Test
    @DisplayName("whenFindAllThenReturnSuccess")
    void findAll() {
        when(service.findAll()).thenReturn(List.of(user, user));
        when(mapper.map(any(), any())).thenReturn(userDTO);
        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertInstanceOf(List.class, response.getBody());
        assertEquals(UserDTO.class, response.getBody().getFirst().getClass());

        assertEquals(ID, response.getBody().getFirst().getId());
        assertEquals(NAME, response.getBody().getFirst().getName());
        assertEquals(MAIL, response.getBody().getFirst().getEmail());
        assertEquals(PASSWORD, response.getBody().getFirst().getPassword());
    }

    @Test
    @DisplayName("whenCreateThenReturnSuccess")
    void create() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
    user = new User(ID, NAME, MAIL, PASSWORD);
    userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
    }
}