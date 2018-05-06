package ru.walkaround.walkaround.model;

public class Category {

    private String name;

    private boolean selected;

    private int imageId;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getImage() {
        return imageId;
    }

    public void setImage(int id) {
        this.imageId = id;
    }
}
