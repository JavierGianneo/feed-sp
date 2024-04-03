package com.feed.feedsp.service;

import com.feed.feedsp.entity.Post;
import com.feed.feedsp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository repository;

  @Override
  public Post createPost(Post post) {
    return repository.save(post);
  }

  @Override
  public List<Post> getFeed() {
    return (List<Post>) repository.findAll();
  }

  @Override
  public void deletePost(Long id) throws Exception {
    Optional<Post> post = repository.findById(id);
    if (post.isEmpty()) {
      throw new Exception("post not found");
    }
    repository.delete(post.get());
  }
}
