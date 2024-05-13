package com.images_server.server_v2.repositories;

import com.images_server.server_v2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Long> {

    @Override
    Optional<Person> findById(Long id);

    Optional<Person> findByName(String name);

    List<Person> findByMale(boolean male);

    Optional<Person> findTopByOrderByNumberOfImagesAsc();

    Optional<Person> findTopByOrderByNumberOfImagesDesc();

    boolean existsByName(String name);

    List<Person> findByImages_Properties_Name(String name);


}
