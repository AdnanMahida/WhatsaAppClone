package com.ad.whatsappclone.models;

public class Status {
    private String imageVideoUrl;
    private long timestamp;

    public Status() {
    }

    public String getImageVideoUrl() {
        return imageVideoUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setImageVideoUrl(String imageVideoUrl) {
        this.imageVideoUrl = imageVideoUrl;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
