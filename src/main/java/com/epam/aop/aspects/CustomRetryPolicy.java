package com.epam.aop.aspects;

import org.springframework.classify.Classifier;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.io.IOException;
import java.util.List;

public class CustomRetryPolicy extends ExceptionClassifierRetryPolicy {

    private List< Class<? extends Throwable>> ignoreExceptions;

    private int maxAttempts;

    public void createCustomRetryPolicy(){
        this.setExceptionClassifier(new Classifier<Throwable, RetryPolicy>() {
            @Override
            public RetryPolicy classify(Throwable classifiable) {
                return handleErrorCode(classifiable);
            }
        });
    }

    public CustomRetryPolicy(List<Class<? extends Throwable>> ignoreExceptions, int maxAttempts) {
        this.ignoreExceptions = ignoreExceptions;
        this.maxAttempts = maxAttempts;
    }

    public CustomRetryPolicy() {
    }

    private RetryPolicy handleErrorCode(Throwable e){

        RetryPolicy retryPolicy = null;

        if(ignoreExceptions.stream().filter(er->er.isAssignableFrom(e.getClass())).findAny().isPresent()) {
            retryPolicy = doNotRetry();
            return retryPolicy;
        }
        else if (IOException.class.isAssignableFrom(e.getClass())){
            retryPolicy = simpleRetryPolicy();
        }
        return retryPolicy;
    }

    private RetryPolicy simpleRetryPolicy() {
        final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(maxAttempts);
        return simpleRetryPolicy;
    }

    private RetryPolicy doNotRetry() {

        return new NeverRetryPolicy();
    }
}
