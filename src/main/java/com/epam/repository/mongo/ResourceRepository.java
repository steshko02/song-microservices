package com.epam.repository.mongo;

import com.epam.model.resource.ResourceObj;
import com.epam.model.entity.StorageType;

import java.util.List;

public interface ResourceRepository {
    ResourceObj getResource(StorageType storageType );
    ResourceObj saveResource(ResourceObj resourceObj);
    ResourceObj getResourceById(String id);
    List<ResourceObj> getByStorageId(String  storageId);
}
