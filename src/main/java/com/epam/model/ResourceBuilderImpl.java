package com.epam.model;

import com.epam.model.resource.CompressionResource;
import com.epam.model.resource.ResourceObj;
import com.epam.model.resource.temp.TempResource;
import com.epam.model.resource.temp.TempResourceWithInputStream;
import com.epam.model.resource.threshold.ThresholdBasedTempResourceFactory;
import com.epam.tempfactory.TempResourceFactory;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

public class ResourceBuilderImpl implements ResourceBuilder {

    private ResourceObj resourceObj;

    public ResourceBuilderImpl(ResourceObj resourceObj) {
        this.resourceObj = resourceObj;
    }

    @Override
    public ResourceBuilder withCompression() {
        CompressionResource compressionResource = new CompressionResource(resourceObj);
        this.resourceObj = compressionResource;
        return  this;
    }


    @Override
    public ResourceObj build() {
        return resourceObj;
    }
}
