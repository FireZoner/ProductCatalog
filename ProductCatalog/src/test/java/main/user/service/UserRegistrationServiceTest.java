package main.user.service;

import main.user.domain.AppUser;
import main.user.domain.AppUserRepository;
import main.user.web.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author zubbo
 */
public class UserRegistrationServiceTest {

    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final UserRegistrationService userRegistrationService =
            new UserRegistrationService(appUserRepository, passwordEncoder);

    @Test
    void shouldRegisterNewUser() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFullName("Степан");
        request.setEmail("stepan@example.com");
        request.setPassword("password123");

        when(appUserRepository.existsByEmail("stepan@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        userRegistrationService.register(request);

        verify(appUserRepository).existsByEmail("stepan@example.com");
        verify(passwordEncoder).encode("password123");

        verify(appUserRepository).save(argThat((AppUser user) ->
                user.getEmail().equals("stepan@example.com")
                        && user.getFullName().equals("Степан")
                        && user.getPasswordHash().equals("encoded-password")
                        && user.getRole() != null
        ));

        verifyNoMoreInteractions(appUserRepository, passwordEncoder);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFullName("Степан");
        request.setEmail("stepan@example.com");
        request.setPassword("password123");

        when(appUserRepository.existsByEmail("stepan@example.com"))
                .thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userRegistrationService.register(request));

        verify(appUserRepository).existsByEmail("stepan@example.com");
        verifyNoInteractions(passwordEncoder);
        verify(appUserRepository, never()).save(any());
    }
}
