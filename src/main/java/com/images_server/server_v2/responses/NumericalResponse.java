package com.images_server.server_v2.responses;

public class NumericalResponse {
    long value;

    public NumericalResponse(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
