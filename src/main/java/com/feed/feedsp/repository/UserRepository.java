package com.feed.feedsp.repository;

import com.feed.feedsp.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  boolean existsByUserName(String userName);

  Optional<User> findByUserName(String userName);
}
