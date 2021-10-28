package com.epam.model.storages;

import com.epam.model.ResourceBuilder;
import com.epam.model.resource.ResourceObj;
import com.epam.model.entity.StorageType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public interface Storage {
    StorageType getType();
    ResourceObj createNewResource();
    String getId();
    ResourceBuilder requestBuilder();

}
