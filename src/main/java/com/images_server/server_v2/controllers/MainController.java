package com.images_server.server_v2.controllers;



import com.images_server.server_v2.models.Image;
import com.images_server.server_v2.models.Property;
import com.images_server.server_v2.models.Race;
import com.images_server.server_v2.repositories.ImageRepo;
import com.images_server.server_v2.repositories.PersonRepo;
import com.images_server.server_v2.repositories.PropertyRepo;
import com.images_server.server_v2.repositories.RaceRepo;
import com.images_server.server_v2.responses.*;
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
import java.util.Map;


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

    @Autowired
    private RaceRepo raceRepo;

    @PostMapping("/add-person")
    public PersonResponse addPerson(@RequestParam("name") String name,
                                    @RequestParam("gender") String gender,
                                    @RequestParam("race") String race) {

        return dbService.savePerson(name, gender, race);
    }

    @PostMapping("/add-persons")
    public List<PersonResponse> addPersons(@RequestParam("names") String[] names,
                                           @RequestParam("genders") String[] genders,
                                           @RequestParam("races") String[] races) {

        List<PersonResponse> responses = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            responses.add(dbService.savePerson(names[i], genders[i], races[i]));
        }

        return responses;
    }

    @GetMapping("/get-persons")
    public List<PersonResponse> getPersons() {

        return personRepo.findAll().stream()
                .map(PersonResponse::new)
                .toList();
    }

    @PostMapping("/update-person")
    public PersonResponse updatePerson(@RequestParam("name") String name,
                                       @RequestParam("gender") String gender,
                                       @RequestParam("race") String race) {
        return dbService.updatePerson(name, gender, race);
    }

    @PostMapping("/add-properties")
    public PropertyResponse addProperties(@RequestParam("properties") String [] properties) {

        return dbService.saveProperties(properties);
    }

    @GetMapping("/get-properties")
    public PropertyResponse getProperties() {

        Iterable<Property> properties = propertyRepo.findAll();
        int n = 0;
        for (Property property : properties) {
            n++;
        }
        String [] stringProperties = new String[n];
        int i = 0;
        for (Property property : properties) {
            stringProperties[i] = property.getName();
            i++;
        }

        return new PropertyResponse(stringProperties, "");
    }

    @PostMapping("/add-races")
    public RacesResponse addRaces(@RequestParam("races") String [] races) {

        return dbService.saveRaces(races);
    }

    @GetMapping("/get-races")
    public RacesResponse getRaces() {

        Iterable<Race> races = raceRepo.findAll();
        int n = 0;
        for (Race race : races) {
            n++;
        }
        String [] stringRaces = new String[n];
        int i = 0;
        for (Race race : races) {
            stringRaces[i] = race.getName();
            i++;
        }
        return new RacesResponse(stringRaces);
    }

    @PostMapping("/upload")
    public ResponseToUpload uploadFile(@RequestParam("file") MultipartFile file,
                                   @RequestParam("personName") String personName,
                                   @RequestParam("properties") String[] properties) {

        return dbService.saveImage(file, personName, properties);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/get-all-images")
    public ImagesUrisResponse getAllImages(){

        Iterable<Image> images = imageRepo.findAll();

        int n = 0;
        for (Image image : images) {
            n++;
        }

        String [] uris = new String[n];
        int i = 0;
        for (Image image : images) {
            uris[i] = image.getUri();
            i++;
        }

        return new ImagesUrisResponse(uris);
    }


    @GetMapping("/get-images-by/{option}/{value}")
    public ImagesUrisResponse getImagesByOption(@PathVariable Map<String, String> pathVars) {

        String option = pathVars.get("option");
        String value = pathVars.get("value");

        List<Image> images;

        if (option.equals("person-name"))
            images = imageRepo.findByPerson_Name(value);
        else if (option.equals("gender"))
            images = value.equals("male") ?
                    imageRepo.findByPerson_Male(true) : imageRepo.findByPerson_Male(false);
        else if (option.equals("property"))
            images = imageRepo.findByProperties_Name(value);
        else
            images = imageRepo.findAll();

        return new ImagesUrisResponse(images.stream().map(Image::getUri).toArray(String[]::new));
    }

    @GetMapping("/get-image-info")
    public ResponseToUpload getImageInfo(@RequestParam("filename") String filename) {

        return new ResponseToUpload(imageRepo.findByFilename(filename).get(), "");
    }

    /**
    @GetMapping("/get-images-by-person-name-and-option")
    public ImagesUrisResponse getImagesByMultipleOptions(@RequestParam("personName") String personName,
                                                         @RequestParam("option") String option,
                                                         @RequestParam("value") String value) {

        for (Map.Entry<String, String> entry : pathVars.entrySet()) {

        }
        List<Image> images;
    }
     */



}
