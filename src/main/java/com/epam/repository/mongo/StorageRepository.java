package com.epam.repository.mongo;

import com.epam.model.storages.Storage;
import com.epam.model.entity.StorageType;

public interface StorageRepository {

    Storage getStorage(StorageType storageType);
    void  saveStorage(Storage storage);
    Storage getStorageById(String id);
}
