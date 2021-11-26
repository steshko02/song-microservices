package com.epam.tempfactory;


import com.epam.model.resource.temp.TempResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface TempResourceFactory {
    TempResource createTempResource(InputStream inputStream) throws IOException;

    TempResource createTempResource(TempResourceWriter tempResourceWriter) throws IOException;

    @FunctionalInterface
     interface TempResourceWriter{
        void  write(OutputStream outputStream) throws IOException;
    }
}
