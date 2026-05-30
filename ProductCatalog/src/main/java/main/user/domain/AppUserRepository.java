package main.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 *
 * @author zubbo
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);
}