package com.example.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("booked")
    private int booked;

    public Book() {
    }

    public Book(String code, String name, int booked) {
        this.code = code;
        this.name = name;
        this.booked = booked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }
}
