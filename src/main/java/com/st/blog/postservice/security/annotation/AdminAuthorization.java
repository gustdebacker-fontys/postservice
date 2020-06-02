package com.st.blog.postservice.security.annotation;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

//@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@PreAuthorize("isAuthenticated() and hasRole(T(com.st.blog.postservice.security.Roles).ADMINISTRATOR)")
public @interface AdminAuthorization {
}
