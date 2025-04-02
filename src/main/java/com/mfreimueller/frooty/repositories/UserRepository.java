package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
