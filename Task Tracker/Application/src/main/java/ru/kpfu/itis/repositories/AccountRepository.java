package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByConfirmCode(UUID code);
}
