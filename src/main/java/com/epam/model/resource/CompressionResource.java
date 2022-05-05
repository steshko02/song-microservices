package com.epam.model.resource;

import com.epam.service.impl.GZIPCompressionOperations;
import com.epam.service.interfaces.CompressionOperation;
import lombok.SneakyThrows;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@Document
@TypeAlias("CompressRes")
public class CompressionResource implements ResourceObj {

    private String id;
    @Transient
    private CompressionOperation compressionOperation;

    private  ResourceObj delegate;

    public CompressionResource( ResourceObj delegate) {
       this.delegate = delegate;
        compressionOperation = new GZIPCompressionOperations();
    }

    @SneakyThrows
    @Override
    public void save(InputStream is) throws IOException {
        try ( InputStream isToUse = is;
                InputStream compressedIs = compressionOperation.compressInputStream(isToUse)) {
            delegate.save(compressedIs);
        }
    }



    @Override
    public InputStream read() throws UncheckedIOException, IOException {
            return compressionOperation.decompressInputStream(delegate.read());
//            return delegate.read();
    }

    @Override
    public InputStream readWithOffset(Long offset, Long length) {
       return delegate.readWithOffset(offset,length);
    }

//    public void retryResponse(IOException e) throws Exception {
//        System.out.println("retry!");
//        delegate.retryResponse(e);
//    }

    @Override
    public void setStorageId(String storageId) {
        delegate.setStorageId(storageId);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getStorageId() {
        return delegate.getStorageId();
    }

    @Override
    public String getPath() {
        return delegate.getPath();
    }

    @Override
    public void delete() {
        delegate.delete();
    }

    @Override
    public void setPath(String path) {
        delegate.setPath(path);
    }

    @Override
    public Class<? extends ResourceObj> supports() {
        return delegate.supports();
    }

    @Override
    public String getFileName() {
        return delegate.getFileName();
    }


    @Override
    public void save(ContentConsumer contentConsumer) throws IOException {
        delegate.save(contentConsumer);
    }
}
