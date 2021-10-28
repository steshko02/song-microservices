package com.epam.service.interfaces;

import com.epam.model.resource.Resource;

import java.util.List;

public interface ResourceService  {
    Long  addResource(Resource resource);
    Resource get(Long id);
    List<Resource> getAll();
    void deleteAll();
    boolean  ifExistsByCheckSum(String str);
}
