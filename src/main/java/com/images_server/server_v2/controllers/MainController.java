package com.images_server.server_v2.controllers;



import com.images_server.server_v2.models.Property;
import com.images_server.server_v2.repositories.ImageRepo;
import com.images_server.server_v2.repositories.PersonRepo;
import com.images_server.server_v2.repositories.PropertyRepo;
import com.images_server.server_v2.responses.PersonResponse;
import com.images_server.server_v2.responses.PropertyResponse;
import com.images_server.server_v2.responses.ResponseToUpload;
import com.images_server.server_v2.services.DBService;
import com.images_server.server_v2.services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;


@RestController
public class MainController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private DBService dbService;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    @PostMapping("/add-person")
    public PersonResponse addPerson(@RequestParam("name") String name,
                                          @RequestParam("gender") String gender) {

        return dbService.savePerson(name, gender);
    }

    @PostMapping("/add-persons")
    public List<PersonResponse> addPersons(@RequestParam("names") String[] names,
                                           @RequestParam("genders") String[] genders) {

        List<PersonResponse> responses = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            responses.add(dbService.savePerson(names[i], genders[i]));
        }

        return responses;
    }

    @GetMapping("/get-persons")
    public List<PersonResponse> getPersons() {

        return personRepo.findAll().stream()
                .map(PersonResponse::new)
                .toList();
    }

    @PostMapping("/add-properties")
    public PropertyResponse addProperties(@RequestParam("properties") String [] properties) {

        return dbService.saveProperties(properties);
    }

    @GetMapping("/get-properties")
    public PropertyResponse getProperties() {

        return new PropertyResponse((String[]) propertyRepo.findAll().stream()
                .map(Property::getName).toArray(), "");
    }

    @PostMapping("/upload")
    public ResponseToUpload uploadFile(@RequestParam("file") MultipartFile file,
                                   @RequestParam("personName") String personName,
                                   @RequestParam("properties") String[] properties) {

        return dbService.saveImage(file, personName, properties);
    }

    /**
    @PostMapping("/upload-file")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }
     */

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
