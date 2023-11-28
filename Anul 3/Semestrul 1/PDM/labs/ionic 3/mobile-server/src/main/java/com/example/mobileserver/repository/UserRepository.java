package com.example.mobileserver.repository;

import com.example.mobileserver.model.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, String> {

  Optional<UserAccount> findByUsername(String username);

  Optional<UserAccount> findByUsernameAndPassword(String username, String password);
}
