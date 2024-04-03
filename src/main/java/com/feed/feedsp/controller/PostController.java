package com.feed.feedsp.controller;

import com.feed.feedsp.entity.Post;
import com.feed.feedsp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

  @Autowired
  public PostService service;

  @GetMapping("/feed")
  public ResponseEntity<List<Post>> getFeed() {
    return ResponseEntity.status(HttpStatus.OK).body(service.getFeed());
  }

  @PostMapping
  public ResponseEntity<Post> post(@RequestBody Post post) {
    return ResponseEntity.status(HttpStatus.OK).body(service.createPost(post));
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable Long id) throws Exception {
    service.deletePost(id);
  }
}
