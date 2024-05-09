package com.images_server.server_v2.responses;

public class RacesResponse {

    private String [] races;

    public RacesResponse(String[] races) {
        this.races = races;
    }

    public String[] getRaces() {
        return races;
    }

    public void setRaces(String[] races) {
        this.races = races;
    }
}
