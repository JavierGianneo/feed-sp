package com.feed.feedsp.repository;

import com.feed.feedsp.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
