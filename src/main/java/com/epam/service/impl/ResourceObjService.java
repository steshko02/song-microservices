package com.epam.service.impl;

import com.epam.jms.ResourceMessageProducer;
import com.epam.model.resource.ResourceObj;
import com.epam.model.storages.Storage;
import com.epam.model.entity.StorageType;
import com.epam.repository.mongo.ResourceObjRepository;
import com.epam.repository.mongo.StorageRepository;
import com.epam.service.interfaces.ResourceObjectService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class ResourceObjService implements ResourceObjectService {

    @Autowired
    private ResourceObjRepository resourceObjRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private ResourceMessageProducer producer;

    public void store(InputStream inputStream, StorageType storageType, String ex) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Storage storage = storageRepository.getStorage(storageType);
//        ResourceObj resource = storage.requestBuilder().withCompression().build();
        ResourceObj resource = storage.requestBuilder().build();
        resourceObjRepository.saveResource(resource);
        resource.save(inputStream);
        producer.sendMessage(resource.getId());
    }

    public  List<String> loadAll(StorageType storageType) throws IOException {
        Storage storage = storageRepository.getStorage(storageType);
       return resourceObjRepository.getByStorageId(storage.getId()).stream().
               map(resourceObj -> resourceObj.getFileName()).collect(Collectors.toList());
    }

    @Override
    public ResourceObj getResource(String id) {
        return resourceObjRepository.getResourceById(id);
    }
}
