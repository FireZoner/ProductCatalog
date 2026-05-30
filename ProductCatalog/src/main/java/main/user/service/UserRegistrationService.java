package main.user.service;

import main.user.domain.AppUser;
import main.user.domain.AppUserRepository;
import main.user.domain.UserRole;
import main.user.web.RegisterUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zubbo
 */
@Service
public class UserRegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterUserRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Пользователь с таким email уже существует");
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        AppUser user = new AppUser(
                request.getEmail(),
                passwordHash,
                request.getFullName(),
                UserRole.USER
        );

        appUserRepository.save(user);
    }
}
