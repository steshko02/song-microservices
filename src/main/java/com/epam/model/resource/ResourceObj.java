package com.epam.model.resource;

import io.minio.errors.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface ResourceObj {
//
////
//    @Retryable(value = {UnknownHostException.class},maxAttemptsExpression = "3",
//            backoff = @Backoff(delayExpression = "400"))
    InputStream read() throws UncheckedIOException, IOException;

//    @Recover
//    void retryResponse(IOException e) throws Exception;

    void save(InputStream stream) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    void setStorageId(String storageId);
    String getId();
    String getStorageId();
    String getPath();
    void delete();
    void setPath(String path);
    Class<? extends ResourceObj>  supports();
    String getFileName();

    void  save(ContentConsumer contentConsumer) throws IOException;

    @FunctionalInterface
    interface ContentConsumer {
          void writeContent() throws IOException;
    }
}
