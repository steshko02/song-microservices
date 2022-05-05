package com.epam.model.resource;

import com.epam.annotations.RetryOnFailure;
import com.epam.model.resource.temp.SizeAwareInputStream;
import com.epam.model.resource.temp.TempResource;
import com.epam.model.resource.threshold.ThresholdBasedTempResourceFactory;
import io.minio.*;
import io.minio.errors.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Document
@Data
@TypeAlias("MinioResource")
public class CloudStorageEntity implements ResourceObj {

    @Transient
    public static final String SEQUENCE_NAME = "res_sequence";

    private String id;
    private String storageId;

    private String URL ;
    private String accessKey;
    private String secretKey;

    private  String bucket;
    private String name;
    @Transient
    private MinioClient minioClient;

    public CloudStorageEntity() {

    }

    public CloudStorageEntity(String storageId, String URL, String accessKey, String secretKey, String bucket,String name) {
        this.storageId = storageId;
        this.URL = URL;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
        this.name = name;
    }

    public CloudStorageEntity(String storageId, String URL, String accessKey, String secretKey, String bucket) {
        this.storageId = storageId;
        this.URL = URL;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
        createFileName();
    }

    private MinioClient buildClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
         try {
             MinioClient minioClient =
                     MinioClient.builder()
                             .endpoint(URL)
                             .credentials(accessKey, secretKey)
                             .build();
             if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(this.bucket).build())) {
                 minioClient.makeBucket(MakeBucketArgs.builder()
                         .bucket(this.bucket)
                         .build());
             }
             return minioClient;
         }catch (UnknownHostException e){
             throw new UncheckedIOException(new IOException("bad connect"));
         }
    }

    @SneakyThrows
    @Override
    public InputStream read()  throws UncheckedIOException{
        try {
            if (minioClient == null) this.minioClient = buildClient();
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(this.bucket)
                    .object(this.name)
                    .build();
            return minioClient.getObject(getObjectArgs);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    @SneakyThrows
    @Override
    public InputStream readWithOffset(Long offset,Long length) {
        try {
            if (minioClient == null) this.minioClient = buildClient();
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                        .bucket(this.bucket)
                        .object(this.name)
                        .offset(offset)
                        .length(length)
                        .build();
            return minioClient.getObject(getObjectArgs);
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void save(InputStream stream) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if(SizeAwareInputStream.class.isAssignableFrom(stream.getClass())){
            saveStreamWithSize(stream, ((SizeAwareInputStream) stream).getInputStreamSize()); return;
        }else{
            try(TempResource tempResource = ThresholdBasedTempResourceFactory.defaults()
                    .createTempResource(stream);
                InputStream inputStream = tempResource.getInputStream()) {
                saveStreamWithSize(inputStream, tempResource.getSize());
            }
        }
    }
    private void saveStreamWithSize(InputStream stream,Long size) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        this.minioClient = buildClient();
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucket).object(name).stream(
                                stream, size, -1)
                        .build());
    }

    private void createFileName(){
        UUID uuid = UUID.randomUUID();
        this.name = uuid.toString() +".mp3";
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void delete() {

    }
    @Override
    public Class<? extends ResourceObj> supports() {
        return this.getClass();
    }

    @Override
    public String getFileName() {
        return  this.name;
    }

    @SneakyThrows
    @Override
    public void save(ContentConsumer contentConsumer) throws IOException {
        contentConsumer.writeContent();
    }

    @Override
    public void setPath(String path) {

    }

//    @Override
//    public void writeContent(OutputStream outputStream) throws IOException {
//        try{
//            IOUtils.copy(read(),outputStream);
//        }catch (IOException e){
//            throw e;
//        }
//    }
}

