package com.st.blog.postservice.entity;

import com.st.blog.postservice.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int branch;

    @NotNull
    @Size(min = 240, max = 10000)
    private String article;

    @NotNull
    private String featuredImage;

    @NotNull
    private int authorId;

    private Date datePublished = new Date();
    private Boolean commentsEnabled = true;
    private Boolean enabled = false;
    private int views;
    private String snippetTitle;
    private String snippetDescription;

    @ManyToMany
    Set<Category> categories;

    @Transient
    private Set<Comment> comments;
}
