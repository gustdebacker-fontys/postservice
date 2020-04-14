package com.st.blog.postservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Comment {
  private int id;
  private String comment;
  private int userId;
  private int postId;
  private int isReplyToId;
  private Boolean readByAuthor;
  private Boolean enabled;
  private Date commented;
}
