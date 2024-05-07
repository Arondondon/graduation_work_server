package com.images_server.server_v2.services;

import com.images_server.server_v2.responses.PersonResponse;
import com.images_server.server_v2.responses.PropertyResponse;
import com.images_server.server_v2.responses.ResponseToUpload;
import org.springframework.web.multipart.MultipartFile;

public interface DBService {

    public ResponseToUpload saveImage(MultipartFile file, String personName, String[] properties);

    public PersonResponse savePerson(String name, String gender);

    public PropertyResponse saveProperties(String [] properties);
}
