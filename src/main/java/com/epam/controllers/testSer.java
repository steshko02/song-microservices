package com.epam.controllers;

import com.epam.annotations.RetryOnFailure;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;
import java.io.UncheckedIOException;

@Retryable
public class testSer {

    @RetryOnFailure(retryAttempts = 2,sleepInterval = 2000L)
    public String retry() throws UncheckedIOException {
        System.out.println("this is try");
        throw new UncheckedIOException(new IOException("bad connect"));
    }

    public void rec(UncheckedIOException e) throws IOException {
        System.out.println("GG");
        throw new IOException(e);
    }
}

