package com.images_server.server_v2.repositories;

import com.images_server.server_v2.models.Image;
import com.images_server.server_v2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepo extends JpaRepository<Image, Long> {

    @Override
    Optional<Image> findById(Long id);

    List<Image> findByPerson(Person person);

    Optional<Image> findByFilename(String filename);

}
