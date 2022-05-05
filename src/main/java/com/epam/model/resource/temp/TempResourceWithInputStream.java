package com.epam.model.resource.temp;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TempResourceWithInputStream extends FilterInputStream {

    private  final  TempResource tempResource;

    public TempResourceWithInputStream(TempResource tempResource) {
        super(tempResource.getInputStream());
        this.tempResource = tempResource;
    }
    @Override
    public  int read() throws IOException {
        int a = super.read();
        return a;
    }

    public InputStream readInputStream(){
        return tempResource.getInputStream();
    }

    @Override
    public void close() throws IOException{
        super.close();
        tempResource.close();
    }
}
