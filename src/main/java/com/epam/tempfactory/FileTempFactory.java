package com.epam.tempfactory;

import com.epam.model.resource.temp.FileTempResource;
import com.epam.model.resource.temp.TempResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
@Slf4j
public class FileTempFactory implements TempResourceFactory {

    public  static  String  FILE_PREFIX = "song-temp-file";
    public  static  String  FILE_POSTFIX = ".tmp";

    public final String filePrefix;
    public final String filePostfix;

    public FileTempFactory() {
        this.filePrefix = FILE_PREFIX;
        this.filePostfix = FILE_POSTFIX;
    }

    @Override
    public TempResource createTempResource(InputStream inputStream) throws IOException {
        File tmpFile = File.createTempFile(filePrefix,filePostfix);
        try (OutputStream outputStream = new FileOutputStream(tmpFile)){
            IOUtils.copy(inputStream,outputStream);
        }catch (Exception e){
            deleteFile(tmpFile);
        }
        return  new FileTempResource(tmpFile);
    }

    @Override
    public TempResource createTempResource(TempResourceWriter tempResourceWriter) throws IOException {
        File tmpFile = File.createTempFile(filePrefix,filePostfix);
        try (OutputStream outputStream = new FileOutputStream(tmpFile)){
            tempResourceWriter.write(outputStream);
        }catch (Exception e){
            deleteFile(tmpFile);
            throw  e;
        }
        return new FileTempResource(tmpFile);
    }

    private  void deleteFile(File file){
        boolean success= file.delete();
        if(!success){
           log.error("cannot delete temp file");
        }
    }
}
