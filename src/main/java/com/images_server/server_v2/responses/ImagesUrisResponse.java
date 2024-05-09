package com.images_server.server_v2.responses;

public class ImagesUrisResponse {

    private int amount;

    private String [] uris;

    public ImagesUrisResponse(String[] uris) {
        this.uris = uris;
        this.amount = uris.length;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String[] getUris() {
        return uris;
    }

    public void setUris(String[] uris) {
        this.uris = uris;
    }
}
