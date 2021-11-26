package com.epam.annotations;

import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public  @interface RetryOnFailure {

    int retryAttempts() default 3;

    long sleepInterval() default 1000L;

    Class<? extends Throwable>[] ignoreExceptions() default { RuntimeException.class };
}
