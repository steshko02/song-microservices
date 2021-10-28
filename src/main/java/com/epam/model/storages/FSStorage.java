package com.epam.model.storages;

import com.epam.model.*;
import com.epam.model.entity.StorageType;
import com.epam.model.resource.FileStorageEntity;
import com.epam.model.resource.ResourceObj;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document
@TypeAlias("FS")
public class FSStorage implements Storage {

    @Id
    private  String id;
    private String path;
    private StorageType storageType  = StorageType.DISK_FILE_SYSTEM;

    public FSStorage(String id, String path, StorageType storageType) {
        this.id = id;
        this.path = path;
        this.storageType= storageType;
    }

    public FSStorage() {
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

    @Override
    public ResourceObj createNewResource() {
        ResourceObj resourceObj = new FileStorageEntity(id,path);
        return resourceObj;
    }

    @Override
    public ResourceBuilder requestBuilder() {
        return new ResourceBuilderImpl(createNewResource());
    }

}
