package com.epam.tempfactory;


import com.epam.model.resource.temp.ByteArrayTempResource;
import com.epam.model.resource.temp.TempResource;
import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayTempResourceFactory implements TempResourceFactory {
    @Override
    public TempResource createTempResource(InputStream inputStream) throws IOException {
        return new ByteArrayTempResource(ByteStreams.toByteArray(inputStream));
    }

    @Override
    public TempResource createTempResource(TempResourceWriter tempResourceWriter)  throws IOException{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            tempResourceWriter.write(outputStream);
            return new ByteArrayTempResource(outputStream.toByteArray());
        }

}
