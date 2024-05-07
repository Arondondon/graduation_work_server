package com.images_server.server_v2.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    private String uri;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "properties_images",
            joinColumns = {
                    @JoinColumn(name = "image_id", referencedColumnName = "id",
                            nullable = false, insertable=false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "property_id", referencedColumnName = "id",
                            nullable = false, insertable=false, updatable = false)
            })
    private Set<Property> properties = new HashSet<>();

    public Image() {}

    public Image(String filename, Person person, String uri) {
        this.filename = filename;
        this.person = person;
        this.uri = uri;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
}
