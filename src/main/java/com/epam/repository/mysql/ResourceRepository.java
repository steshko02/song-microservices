package com.epam.repository.mysql;


import com.epam.model.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource,Long> {
    boolean existsByPath(String path);
    boolean existsByChecksum(String sum);
}
