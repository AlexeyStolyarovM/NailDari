package org.example.naildari.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SpringClientServiceImplTest {

    @SpyBean
    ClientService clientService;

    @Test
    void getClientById() {
        var result = clientService.getClientById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        System.out.println(result);
    }

}