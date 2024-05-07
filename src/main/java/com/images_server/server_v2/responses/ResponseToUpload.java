package com.images_server.server_v2.responses;


public class ResponseToUpload {
    private String filename;
    private String person;
    private String gender;
    private String[] properties;
    private String uri;
    private String type;
    private long size;
    private String comment;

    public ResponseToUpload(String filename, String person, boolean isMale, String[] properties, String uri,
                            String type, long size, String comment) {
        this.filename = filename;
        this.person = person;
        this.gender = isMale ? "male" : "female";
        this.properties = properties;
        this.uri = uri;
        this.type = type;
        this.size = size;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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
}
