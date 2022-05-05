package com.epam.service.dtointerfaces;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.dto.AlbumDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface AlbumDtoService {
    Long add(AlbumDto albumDto);

    Long edit(AlbumDto albumDto, Long id) throws EntityNotFoundException;

    AlbumDto getAlbum(Long id) throws EntityNotFoundException;

    Long delete(Long id) throws EntityNotFoundException;
}
