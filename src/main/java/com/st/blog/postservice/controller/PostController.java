package com.st.blog.postservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.*;
import com.st.blog.postservice.entity.Post;
import com.st.blog.postservice.model.Comment;
import com.st.blog.postservice.repository.PostRepository;
import com.st.blog.postservice.security.annotation.AdminAuthorization;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {
  public PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  private final PostRepository postRepository;

  private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

  @GetMapping
//  @RolesAllowed("ROLE_ADMINISTRATOR")
  public Page<Post> findAll(Pageable pageable){
    var posts = postRepository.findAll(pageable);


    for(Post post : posts){
      post = addCommentsToPost(post);
    }

    return posts;
  }

  @GetMapping("/{id}")
  public Post findById(@PathVariable("id") int id){
    var post = postRepository.findById(id);

    return addCommentsToPost(post.get());
  }



  private Post addCommentsToPost(Post post){
    var page = 0;
    var pageSize = 10;
    var url = "http://localhost:8083/comments/search/byPostId?page=" + page + "&size=" + pageSize + "&postId=" + post.getId();

    try {
      HttpRequest httpRequest = HttpRequest.newBuilder().GET()
        .uri(URI.create(url))
        .setHeader("content-type", "application/json").build();

      HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
      JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

      JsonObject posts = jsonObject.get("_embedded").getAsJsonObject();
      JsonArray jsonArray = posts.getAsJsonArray("comments");

      Comment[] commentList = new GsonBuilder().create().fromJson(jsonArray, Comment[].class);

      Set<Comment> commentSet = new HashSet<Comment>();
      for (Comment comment : commentList){
        commentSet.add(comment);
      }

      post.setComments(commentSet);
    }
    catch (Exception e){
      e.printStackTrace();
    }

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
