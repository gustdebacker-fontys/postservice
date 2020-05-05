package com.st.blog.postservice.controller;

import com.st.blog.postservice.entity.Post;
import com.st.blog.postservice.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
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

  @PostMapping("/")
  public ResponseEntity<Post> create(@RequestBody Post post) throws URISyntaxException {
    Post createdPost = postRepository.save(post);
    if (createdPost == null){
      return ResponseEntity.notFound().build();
    }
    else{
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPost.getId()).toUri();
      return ResponseEntity.created(uri).body(createdPost);
    }
  }
}
