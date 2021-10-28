package com.epam.model.storages;

import com.epam.model.*;
import com.epam.model.entity.StorageType;
import com.epam.model.resource.CloudStorageEntity;
import com.epam.model.resource.ResourceObj;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@TypeAlias("MS")
public class MinioStorage implements Storage{

    @Id
    private String id;
    private  String  bucket;
    private StorageType storageType  = StorageType.CLOUD_SYSTEM;

    private String URL ;
    private String accessKey;
    private  String secretKey;

//    s3.s3Url=https://play.min.io
//    s3.accessKey=Q3AM3UQ867SPQQA43P2F
//    s3.secretKey=zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG

    public MinioStorage(String id,String bucket,StorageType storageType) {
        this.setBucket(bucket);
        this.setId(id);
        this.setStorageType(storageType);
    }

    public MinioStorage(String id,String bucket, StorageType storageType, String URL, String accessKey, String secretKey) {
        this.setId(id);
        this.bucket = bucket;
        this.storageType = storageType;
        this.URL = URL;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public MinioStorage() {
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

    @Override
    public ResourceObj createNewResource() {
        return new CloudStorageEntity(this.id,this.URL,this.accessKey,this.secretKey,this.bucket);
    }

    @Override
    public ResourceBuilder requestBuilder() {
        return new ResourceBuilderImpl(createNewResource());
    }
}
