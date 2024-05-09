package com.images_server.server_v2.responses;

import com.images_server.server_v2.models.Person;
import com.images_server.server_v2.models.Race;

public class PersonResponse {

    private String name;
    private String gender;
    private String comment;
    private String race;
    private int numberOfImages;

    public PersonResponse(String name, String gender, String comment, int numberOfImages, String race) {
        this.name = name;
        this.gender = gender;
        this.comment = comment;
        this.numberOfImages = numberOfImages;
        this.race = race;
    }

    public PersonResponse(Person person) {
        this.name = person.getName();
        this.gender = person.isMale() ? "male" : "female";
        this.comment = "";
        this.numberOfImages = person.getNumberOfImages();
        this.race = person.getRace().getName();
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

    public int getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(int numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
