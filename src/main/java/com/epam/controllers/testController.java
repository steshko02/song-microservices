package com.epam.controllers;


import com.epam.jms.Producer;
import com.epam.model.entity.StorageType;
import com.epam.model.storages.FSStorage;
import com.epam.model.storages.Storage;
import com.epam.repository.mongo.ResourceRepository;
import com.epam.repository.mongo.StorageRepository;
import com.epam.service.impl.GZIPCompressionOperations;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
public class testController {

//    @Autowired
//    private ResourceObjService resourceObjService;
//
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private Producer producer;

    @Autowired
    private ResourceRepository resourceRepository;

    @SneakyThrows
    @GetMapping("/test4")
    public String  testSong1() throws IOException {


        Storage storage = new FSStorage("213123123131dsfsdf3","[dsfdfs",StorageType.DISK_FILE_SYSTEM);

        storageRepository.saveStorage(storage);

        return "audio";
    }

    @GetMapping("/test")
    public String  test() {

        return  null;
    }


}
