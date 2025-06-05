package com.ashvinprajapati.portfolioapp.Models;

public class repoModel {
    String name;
    String description;
    String link;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public repoModel(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }
}
