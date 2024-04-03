package com.feed.feedsp.service;

import com.feed.feedsp.entity.Post;

import java.util.List;

public interface PostService {
  Post createPost(Post post);

  List<Post> getFeed();

  void deletePost(Long id) throws Exception;
}
