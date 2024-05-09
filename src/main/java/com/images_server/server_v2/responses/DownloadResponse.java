package com.images_server.server_v2.responses;

import org.springframework.core.io.Resource;

public class DownloadResponse {

    private Resource resource;

    private ResponseToUpload responseToUpload;

    public DownloadResponse(Resource resource, ResponseToUpload responseToUpload) {
        this.resource = resource;
        this.responseToUpload = responseToUpload;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ResponseToUpload getResponseToUpload() {
        return responseToUpload;
    }

    public void setResponseToUpload(ResponseToUpload responseToUpload) {
        this.responseToUpload = responseToUpload;
    }
}
