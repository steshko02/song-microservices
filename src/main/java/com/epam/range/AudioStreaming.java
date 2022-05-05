package com.epam.range;

import com.epam.model.entity.Song;
import com.epam.model.resource.ResourceObj;
import com.epam.service.impl.ResourceObjService;
import com.epam.service.interfaces.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class AudioStreaming {

    @Autowired
    private SongService songService;
    @Autowired
    ResourceObjService resourceObjService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<byte[]> prepareContent(Long id, String fileType, String range) throws IOException {
        Song song = songService.getById(id);
        ResourceObj resourceObj = resourceObjService.getResource(song.getResourceObjId());
        String fileName = resourceObj.getFileName();

        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize = song.getResource().getSize();
        String fullFileName = fileName + "." + fileType;
        try {
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Type", "audio/" + "mp3")
                        .header("Content-Length", String.valueOf(fileSize))
                        .body(readByteRange(resourceObj, rangeStart, fileSize - 1)); // Read the object and convert it as bytes
            }
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            }else {
                rangeEnd = fileSize-1;
            }
            data = readByteRange(resourceObj, rangeStart, rangeEnd);
        } catch (IOException e) {
            logger.error("Exception while reading the file {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart)); //+1
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", "audio/" + "mp3")
                .header("Accept-Ranges", "1024")
                .header("Content-Length", contentLength)
                .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);
    }

    public byte[] readByteRange(ResourceObj resourceObj, long start, long end) throws IOException {
        try (InputStream stream1 = resourceObj.readWithOffset(start,end-1)){
        return stream1.readAllBytes();
        }

    }
}
