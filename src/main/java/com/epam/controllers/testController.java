package com.epam.controllers;


import com.epam.jms.ResourceMessageProducer;
import com.epam.model.entity.StorageType;
import com.epam.model.storages.FSStorage;
import com.epam.model.storages.Storage;
import com.epam.repository.mongo.ResourceObjRepository;
import com.epam.repository.mongo.StorageRepository;
import com.epam.service.impl.ResourceObjService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;


@RestController
public class testController {

    @Autowired
    private ResourceObjService resourceObjService;

    @Autowired
    private ResourceObjRepository resourceObjRepository;

    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private ResourceMessageProducer producer;

    @SneakyThrows
    @GetMapping("/test4")
    public String  testSong1() throws IOException {
        Storage storage = new FSStorage("213123123131dsfsdf3","[dsfdfs",StorageType.DISK_FILE_SYSTEM);

        storageRepository.saveStorage(storage);

        return "audio";
    }

//    @Autowired
//    private  testSer test;

    @GetMapping("/test")
    public String  test() {
        testSer test = new testSer();
        test.retry();
        return  "sfsdf";
    }
}
