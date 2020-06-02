package com.st.blog.postservice.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

// TODO make it to where specialists can only edit themselves and administrators adn moderators can edit any specialist

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@PreAuthorize("isFullyAuthenticated() and hasAnyRole(" +
        "T(com.st.blog.postservice.security.Roles).ADMINISTRATOR, " +
        "T(com.st.blog.postservice.security.Roles).MODERATOR, " +
        "T(com.st.blog.postservice.security.Roles).SPECIALIST)"
)
public @interface SpecialistAuthorization {
}
