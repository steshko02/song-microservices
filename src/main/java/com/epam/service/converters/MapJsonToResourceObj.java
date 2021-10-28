package com.epam.service.converters;

import com.epam.model.resource.CloudStorageEntity;
import com.epam.model.resource.FileStorageEntity;
import com.epam.model.resource.ResourceObj;
import com.epam.model.entity.StorageType;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MapJsonToResourceObj implements Converter<DBObject, ResourceObj> {

    @Override
    public ResourceObj convert(DBObject source) {
        StorageType storageType = (StorageType) source.get("storageType");
        switch (storageType){
            case CLOUD_SYSTEM: return new CloudStorageEntity();
            case DISK_FILE_SYSTEM: return new FileStorageEntity();
            default: return null;
        }
    }
}