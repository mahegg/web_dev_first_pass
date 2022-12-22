package com.mahegg.first_pass.dto;

public class RecordPostDto {
    private String url;
    private String username;

    public RecordPostDto() {
    }

    public RecordPostDto(String url, String username) {
        this.url = url;
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
