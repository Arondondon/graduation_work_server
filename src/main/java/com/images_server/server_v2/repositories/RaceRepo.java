package com.images_server.server_v2.repositories;

import com.images_server.server_v2.models.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaceRepo extends JpaRepository<Race, Long> {

    @Override
    Optional<Race> findById(Long id);

    Optional<Race> findByName(String name);

    boolean existsByName(String name);
}
