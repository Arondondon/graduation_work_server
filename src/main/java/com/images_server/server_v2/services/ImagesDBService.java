package com.images_server.server_v2.services;

import com.images_server.server_v2.models.Image;
import com.images_server.server_v2.models.Person;
import com.images_server.server_v2.models.Property;
import com.images_server.server_v2.properties.WarningTextProperty;
import com.images_server.server_v2.repositories.ImageRepo;
import com.images_server.server_v2.repositories.PersonRepo;
import com.images_server.server_v2.repositories.PropertyRepo;
import com.images_server.server_v2.responses.PersonResponse;
import com.images_server.server_v2.responses.PropertyResponse;
import com.images_server.server_v2.responses.ResponseToUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class ImagesDBService implements DBService {

    private final String missingPersonOrProperty;
    private final String personAlreadyExists;
    private final String somePropertiesAlreadyExist;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    private boolean checkPersonAndProperties(String personName, String[] properties) {
        for (String property : properties) {
            if (!propertyRepo.existsByName(property))
                return false;
        }
        return personRepo.existsByName(personName);
    }

    @Autowired
    public ImagesDBService(WarningTextProperty properties) {
        this.missingPersonOrProperty = properties.getMissingPersonOrProperty();
        this.personAlreadyExists = properties.getPersonAlreadyExists();
        this.somePropertiesAlreadyExist = properties.getSomePropertiesAlreadyExist();
    }

    @Override
    public ResponseToUpload saveImage(MultipartFile file, String personName, String[] properties) {
        if (!checkPersonAndProperties(personName, properties))
            return new ResponseToUpload("", personName, false, properties, null,
                    null, 0, missingPersonOrProperty);

        String name = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        Person person = updateNumberOfImagesOfPerson(personName);

        Image image = new Image(name, person, uri);
        image.setProperties(new HashSet<>(Arrays.stream(properties)
                .map(property -> propertyRepo.findByName(property).get())
                .toList()));

        imageRepo.save(image);

        return new ResponseToUpload(name, personName, person.isMale(), properties, uri,
                file.getContentType(), file.getSize(), "");
    }

    public Person updateNumberOfImagesOfPerson(String personName){
        Person person = personRepo.findByName(personName).get();
        person.setNumberOfImages(person.getNumberOfImages() + 1);
        personRepo.save(person);
        return person;
    }

    @Override
    public PersonResponse savePerson(String name, String gender) {
        if (personRepo.existsByName(name))
            return new PersonResponse(name, gender, personAlreadyExists,
                    personRepo.findByName(name).get().getNumberOfImages());
        else {
            personRepo.save(new Person(name, gender.equals("male"), 0));
            return new PersonResponse(name, gender, "", 0);
        }
    }

    @Override
    public PropertyResponse saveProperties(String [] properties) {
        boolean exists = false;
        for (String property : properties) {
            if (!propertyRepo.existsByName(property)) {
                propertyRepo.save(new Property(property));
                exists = true;
            }
        }
        return new PropertyResponse(properties, exists ? somePropertiesAlreadyExist : "");
    }


}
