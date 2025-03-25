package com.example.examejljm;
public class Articulo {
    private String title;
    private String doi;

    public Articulo(String title, String doi) {
        this.title = title;
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public String getDoi() {
        return doi;
    }
}
