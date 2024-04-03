package com.feed.feedsp.service;

import com.feed.feedsp.entity.User;

import java.util.List;

public interface UserService {

  List<User> findAll();

  User save(User user);

  boolean existsByUserName(String userName);
}
