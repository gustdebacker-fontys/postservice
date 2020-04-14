package com.st.blog.postservice.controller;

import com.st.blog.postservice.entity.Post;
import com.st.blog.postservice.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
  public PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  private final PostRepository postRepository;

  @GetMapping
  public Page<Post> findAll(Pageable pageable){
    var posts = postRepository.findAll(pageable);
    //TODO zie Loose-Coupling.txt
    return posts;
  }

  @GetMapping("/{id}")
  public Optional<Post> findById(@PathVariable("id") int id){
    var post = postRepository.findById(id);
    //TODO zie Loose-Coupling.txt
    return post;
  }
}
