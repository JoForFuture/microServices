package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testUpdateUserByEmail() {
        // Creazione di un'istanza di UserEntity con i valori da aggiornare
        UserEntity userToUpdate = new UserEntity();
        userToUpdate.setEmail("example@example.com");
        userToUpdate.setPassword("");
        userToUpdate.setRole("");
        userToUpdate.setExtraInfo("New Info");

        // Simulazione del risultato della ricerca dell'utente nel repository
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("example@example.com");
        existingUser.setPassword("oldPassword");
        existingUser.setRole("user");
        existingUser.setExtraInfo("Original info");
        Optional<UserEntity> optionalExistingUser = Optional.of(existingUser);
        when(userRepository.findByEmail(userToUpdate.getEmail())).thenReturn(optionalExistingUser);

        // Chiamata del metodo da testare
        UserEntity updatedUser = userService.updateUserByEmail(userToUpdate).get();

        // Verifica che il metodo save sia stato chiamato con l'entità aggiornata
        verify(userRepository).save(existingUser);

        // Verifica che l'entità restituita sia quella aggiornata
        assertEquals(existingUser.getEmail(), updatedUser.getEmail());
        assertEquals(existingUser.getPassword(), updatedUser.getPassword());
        assertEquals(existingUser.getRole(), updatedUser.getRole());
        
        assertEquals(userToUpdate.getExtraInfo(), updatedUser.getExtraInfo());
        
        assertNotEquals("Original info", updatedUser.getExtraInfo());

       
    }
}

