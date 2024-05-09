package com.images_server.server_v2.responses;


import com.images_server.server_v2.models.Image;
import com.images_server.server_v2.models.Property;

public class ResponseToUpload {
    private String filename;
    private String person;
    private String gender;
    private String race;
    private String[] properties;
    private String uri;
    private String comment;

    public ResponseToUpload(String filename, String person, boolean isMale, String race,
                            String[] properties, String uri, String comment) {
        this.filename = filename;
        this.person = person;
        this.gender = isMale ? "male" : "female";
        this.race = race;
        this.properties = properties;
        this.uri = uri;
        this.comment = comment;
    }

    public ResponseToUpload(Image image, String comment) {
        this.filename = image.getFilename();
        this.person = image.getPerson().getName();
        this.gender = image.getPerson().isMale() ? "male" : "female";
        this.race = image.getPerson().getRace().getName();
        this.properties = image.getProperties().stream().map(Property::getName).toArray(String[]::new);
        this.uri = image.getUri();
        this.comment = comment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
