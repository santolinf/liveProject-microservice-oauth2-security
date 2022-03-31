package com.liveproject.oauth2.resource.health.security.context;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = TestSecurityContextFactory.class)
public @interface TestUser {

    String username();
    String [] authorities() default { "read" };
}
