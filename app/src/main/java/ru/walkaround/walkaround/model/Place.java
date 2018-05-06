package ru.walkaround.walkaround.model;


public class Place {

    private String name;

    private int imageId;

    public Place(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return imageId;
    }

    public void setImage(int imageId) {
        this.imageId = imageId;
    }

    public Place(String name, int imageId) {

        this.name = name;
        this.imageId = imageId;
    }
}
