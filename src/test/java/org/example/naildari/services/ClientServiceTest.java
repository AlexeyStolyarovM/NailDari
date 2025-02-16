package org.example.naildari.services;

import org.example.naildari.models.Client;
import org.example.naildari.repositories.ClientRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientService clientService;

    //перед всеми тестами
    @BeforeAll
    static void setUp() {
        System.out.println("This is start test");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("This is end test");
    }


    @Test
    void shouldGetClientById() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Test");
        client.setFirstName("Test");
        client.setAppointments(new ArrayList<>());
        //замокали репо
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        //вызываем метод сервиса
        Client result = clientService.getClientById(anyLong());
        // Проверяем результат
        assertNotNull(result);
        assertEquals("Test", result.getName());
        // Проверяем, что метод репозитория был вызван
        verify(clientRepository, times(1)).findById(anyLong());
    }
}