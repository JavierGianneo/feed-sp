package com.feed.feedsp.controller;

import com.feed.feedsp.entity.User;
import com.feed.feedsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public List<User> list() {
    return service.findAll();
  }

  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@Valid @RequestBody User user) {
    user.setAdmin(false);
    return create(user);
  }
}
