package com.images_server.server_v2.responses;

import com.images_server.server_v2.models.Person;

public class PersonResponse {

    private String name;
    private String gender;
    private String comment;
    private int numberOfImages;

    public PersonResponse(String name, String gender, String comment, int numberOfImages) {
        this.name = name;
        this.gender = gender;
        this.comment = comment;
        this.numberOfImages = numberOfImages;
    }

    public PersonResponse(Person person) {
        this.name = person.getName();
        this.gender = person.isMale() ? "male" : "female";
        this.comment = "";
        this.numberOfImages = person.getNumberOfImages();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
