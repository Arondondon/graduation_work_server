package com.images_server.server_v2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "warning")
public class WarningTextProperty {

    private String missingPersonOrProperty;
    private String personAlreadyExists;
    private String somePropertiesAlreadyExist;
    private String imageAlreadyExists;

    public String getMissingPersonOrProperty() {
        return missingPersonOrProperty;
    }

    public void setMissingPersonOrProperty(String missingPersonOrProperty) {
        this.missingPersonOrProperty = missingPersonOrProperty;
    }

    public String getPersonAlreadyExists() {
        return personAlreadyExists;
    }

    public void setPersonAlreadyExists(String personAlreadyExists) {
        this.personAlreadyExists = personAlreadyExists;
    }

    public String getSomePropertiesAlreadyExist() {
        return somePropertiesAlreadyExist;
    }

    public void setSomePropertiesAlreadyExist(String somePropertiesAlreadyExist) {
        this.somePropertiesAlreadyExist = somePropertiesAlreadyExist;
    }

    public String getImageAlreadyExists() {
        return imageAlreadyExists;
    }

    public void setImageAlreadyExists(String imageAlreadyExists) {
        this.imageAlreadyExists = imageAlreadyExists;
    }
}
