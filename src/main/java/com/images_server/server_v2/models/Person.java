package com.images_server.server_v2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private boolean male;

    private int numberOfImages;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_id")
    private Race race;

    public Person() {
    }

    public Person(String name, boolean male, Race race, int numberOfImages) {
        this.name = name;
        this.male = male;
        this.numberOfImages = numberOfImages;
        this.race = race;
    }

    public Person(String name, boolean male, Race race) {
        this.name = name;
        this.male = male;
        this.numberOfImages = 0;
        this.race = race;
    }

    public Person(String name) {
        this.name = name;
        this.male = false;
        this.numberOfImages = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public int getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(int numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
