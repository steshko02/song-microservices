package com.epam.service.interfaces;


import com.epam.model.resource.ResourceObj;
import com.epam.model.entity.StorageType;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ResourceObjectService {

     void store(InputStream inputStream, StorageType storageType, String ex) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException ;
     List<String> loadAll(StorageType storageType) throws IOException ;
     ResourceObj getResource(String id);
}

