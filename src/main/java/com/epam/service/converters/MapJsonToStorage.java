package com.epam.service.converters;

import com.epam.model.storages.FSStorage;
import com.epam.model.storages.MinioStorage;
import com.epam.model.storages.Storage;
import com.epam.model.entity.StorageType;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MapJsonToStorage implements Converter<DBObject, Storage> {
    @Override
    public Storage convert(DBObject source) {
        StorageType storageType = (StorageType) source.get("storageType");
        switch (storageType) {
            case CLOUD_SYSTEM:
                return new MinioStorage((String)source.get("_id"),(String) source.get("bucket"),storageType,
                        (String) source.get("URL"),(String) source.get("accessKey"),
                        (String) source.get("secretKey"));
            case DISK_FILE_SYSTEM:
                return new FSStorage((String)source.get("_id"),(String) source.get("path"),storageType);
            default:
                return null;
        }
    }
}
