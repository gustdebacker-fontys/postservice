package com.st.blog.postservice;

import com.st.blog.postservice.entity.Post;
import com.st.blog.postservice.repository.PostRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;


@DataJpaTest
@ExtendWith(SpringExtension.class)
public class PostRepositoryTests {
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  private static String testArticle = "Mauris sagittis eleifend ante vitae maximus. Phasellus egestas eu orci vitae ultricies. " +
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec rutrum, leo sagittis porttitor feugiat, magna tortor fringilla " +
    "nulla, consectetur adipiscing elit. Donec rutrum, leo sagittis";

  private static int postCount;

  private static int articlesOfAuthorCount;

  private static final int branchId = 1;

  private static final int authorId = 1;

  @BeforeEach
  public void setup(){
    var posts = new HashSet<Post>();
    posts.add(new Post(branchId, testArticle, "https://soliloquywp.com/wp-content/uploads/2017/07/test-your-featured-images-wordpress.jpg", authorId));
    posts.add(new Post(branchId, testArticle, "https://soliloquywp.com/wp-content/uploads/2017/07/test-your-featured-images-wordpress.jpg", authorId));

    for (var post : posts){
      testEntityManager.persist(post);
      if(post.getAuthorId() == authorId){
        articlesOfAuthorCount++;
      }
    }

    testEntityManager.flush();

    postCount = posts.size();
  }
}
