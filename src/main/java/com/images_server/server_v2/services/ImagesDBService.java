package com.images_server.server_v2.services;

import com.images_server.server_v2.models.Image;
import com.images_server.server_v2.models.Person;
import com.images_server.server_v2.models.Property;
import com.images_server.server_v2.models.Race;
import com.images_server.server_v2.properties.WarningTextProperty;
import com.images_server.server_v2.repositories.ImageRepo;
import com.images_server.server_v2.repositories.PersonRepo;
import com.images_server.server_v2.repositories.PropertyRepo;
import com.images_server.server_v2.repositories.RaceRepo;
import com.images_server.server_v2.responses.PersonResponse;
import com.images_server.server_v2.responses.PropertyResponse;
import com.images_server.server_v2.responses.RacesResponse;
import com.images_server.server_v2.responses.ResponseToUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class ImagesDBService implements DBService {

    private final String missingPersonOrProperty;
    private final String personAlreadyExists;
    private final String somePropertiesAlreadyExist;
    private final String imageAlreadyExists;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    @Autowired
    private RaceRepo raceRepo;

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
        this.imageAlreadyExists = properties.getImageAlreadyExists();
    }

    @Override
    public ResponseToUpload saveImage(MultipartFile file, String personName, String[] properties) {
        String name = StringUtils.cleanPath(file.getOriginalFilename());
        if (!checkPersonAndProperties(personName, properties))
            return new ResponseToUpload("", personName, false, null, properties, null,
                    missingPersonOrProperty);
        else if (imageRepo.existsByFilename(name)){
            Image existedImage = imageRepo.findByFilename(name).get();
            return new ResponseToUpload(existedImage.getFilename(), existedImage.getPerson().getName(),
                    existedImage.getPerson().isMale(), existedImage.getPerson().getRace().getName(),
                    existedImage.getProperties().toArray(new String[0]),
                    existedImage.getUri(), imageAlreadyExists);
        }


        name = storageService.store(file);
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

        return new ResponseToUpload(name, personName, person.isMale(),
                person.getRace().getName(), properties, uri, "");
    }

    public Person updateNumberOfImagesOfPerson(String personName){
        Person person = personRepo.findByName(personName).get();
        person.setNumberOfImages(person.getNumberOfImages() + 1);
        personRepo.save(person);
        return person;
    }

    @Override
    public PersonResponse savePerson(String name, String gender, String race) {
        if (personRepo.existsByName(name))
            return new PersonResponse(name, gender, personAlreadyExists,
                    personRepo.findByName(name).get().getNumberOfImages(), race);
        else {
            personRepo.save(new Person(name, gender.equals("male"), raceRepo.findByName(race).get()));
            return new PersonResponse(personRepo.findByName(name).get());
        }
    }

    @Override
    public PropertyResponse saveProperties(String [] properties) {
        boolean exists = false;
        for (String property : properties) {
            if (!propertyRepo.existsByName(property))
                propertyRepo.save(new Property(property));
            else
                exists = true;
        }
        return new PropertyResponse(properties, exists ? somePropertiesAlreadyExist : "");
    }

    @Override
    public RacesResponse saveRaces(String [] races){
        for (String race : races) {
            if (!raceRepo.existsByName(race))
                raceRepo.save(new Race(race));
        }
        return new RacesResponse(races);
    }

    @Override
    public PersonResponse updatePerson(String name, String gender, String race){
        Person person = personRepo.findByName(name).get();
        person.setMale(gender.equals("male"));
        person.setRace(raceRepo.findByName(race).get());
        personRepo.save(person);
        return new PersonResponse(person);
    }


}
