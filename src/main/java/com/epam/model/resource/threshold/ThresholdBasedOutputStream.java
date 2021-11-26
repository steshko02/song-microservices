package com.epam.model.resource.threshold;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ThresholdBasedOutputStream extends OutputStream {

    private  Long threshold;

    private Long counter;

    private  OutputStream delegate;

    private  boolean thresholdReached;

    private ByteArrayOutputStream underThresholdOS;

    private  OutputStream aboveThresholdOS;

    public ThresholdBasedOutputStream(ByteArrayOutputStream underThresholdOS,
                                      OutputStream aboveThresholdOS,
                                      Long threshold) {
        thresholdReached = false;
        counter = 0L;
        this.underThresholdOS = underThresholdOS; //memory
        this.aboveThresholdOS = aboveThresholdOS; //fs
        this.threshold = threshold;
        this.delegate = underThresholdOS; //default - memory
    }

    @Override
    public void write(int b) throws IOException {
        delegate.write(b); //wr in memory
        ++counter;
        if(!thresholdReached && counter>=threshold){
            aboveThresholdOS.write(underThresholdOS.toByteArray()); //wrt in fs
            delegate=aboveThresholdOS; // def = fs
            thresholdReached = true;
        }
    }

    @Override
    public  void  close() throws  IOException{
        delegate = null;
        underThresholdOS.close();
        aboveThresholdOS.close();
    }

    boolean isThresholdReached(){
        return thresholdReached;
    }
}
