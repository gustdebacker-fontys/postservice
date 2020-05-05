package com.st.blog.postservice.repository;

import com.st.blog.postservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    @RestResource(path = "/byAuthorId")
    Page<Post> getPostsByAuthorId (Pageable pageable, int authorId);

    @Override
    @RestResource(exported = false)
    Page<Post> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    Optional<Post> findById(Integer integer);
}