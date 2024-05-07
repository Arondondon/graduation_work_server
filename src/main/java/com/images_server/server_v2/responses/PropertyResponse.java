package com.images_server.server_v2.responses;

public class PropertyResponse {

    private String [] properties;
    private String comment;

    public PropertyResponse(String[] properties, String comment) {
        this.properties = properties;
        this.comment = comment;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
