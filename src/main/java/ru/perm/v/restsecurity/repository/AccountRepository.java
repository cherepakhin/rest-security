package ru.perm.v.restsecurity.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.perm.v.restsecurity.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByUsername(String username);
}
