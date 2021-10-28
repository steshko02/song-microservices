package com.epam.model;

import com.epam.model.resource.ResourceObj;

public interface ResourceBuilder {
  ResourceBuilder   withCompression();
  ResourceObj build();
}
