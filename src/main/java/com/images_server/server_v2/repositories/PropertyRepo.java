package com.images_server.server_v2.repositories;

import com.images_server.server_v2.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepo extends JpaRepository<Property, Long> {

    @Override
    Optional<Property> findById(Long id);

    Optional<Property> findByName(String name);

    boolean existsByName(String name);

}
