package com.example.examejljm;

public class Volumen {
    private String issue_id;
    private String title;

    public Volumen(String issue_id, String title) {
        this.issue_id = issue_id;
        this.title = title;
    }

    public String getIssue_id() {
        return issue_id;
    }

    public String getTitle() {
        return title;
    }
}
